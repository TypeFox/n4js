/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package eu.numberfour.n4js.scoping.accessModifiers;

import static java.util.Collections.emptyList;
import static org.eclipse.xtext.util.Strings.emptyIfNull;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;

import eu.numberfour.n4js.n4mf.ProjectType;
import eu.numberfour.n4js.projectModel.IN4JSCore;
import eu.numberfour.n4js.projectModel.IN4JSProject;
import eu.numberfour.n4js.resource.N4JSResource;
import eu.numberfour.n4js.ts.types.IdentifiableElement;
import eu.numberfour.n4js.ts.types.TModule;
import eu.numberfour.n4js.ts.types.TypeAccessModifier;

/**
 * Abstract visibility checker that implements the logic how a type access modifier is evaluated in a given context.
 */
public abstract class AbstractTypeVisibilityChecker<T extends IdentifiableElement>
		extends AbstractVisibilityChecker<T> {

	/** The N4JS core. This service is used for resolving projects by its contained modules. */
	@Inject
	protected IN4JSCore core;

	/**
	 * Returns the TypeVisibility of the <i>element</i> in the given <i>context</i>(that is the given resource) if it
	 * had the give access modifier. That is, the actual access modifier of the element is not considered here, usually
	 * this is done in the caller via {@code getTypeAccessModifier}. However, there is no common interface for
	 * retrieving that information.
	 */
	protected TypeVisibility isVisible(final Resource contextResource, final TypeAccessModifier accessModifier,
			final T element) {
		int startIndex = accessModifier.getValue();

		boolean visibility = false;
		String firstVisible = "PUBLIC";

		for (int i = startIndex; i < TypeAccessModifier.values().length; i++) {

			boolean visibilityForModifier = false;
			TypeAccessModifier modifier = TypeAccessModifier.get(i);

			switch (modifier) {
			case PRIVATE: {
				visibilityForModifier = isPrivateVisible(contextResource, element);
				break;
			}
			case PROJECT: {
				visibilityForModifier = isProjectVisible(contextResource, element);
				break;
			}
			case PUBLIC_INTERNAL: {
				visibilityForModifier = isPublicInternalVisible(contextResource, element);
				break;
			}
			case PUBLIC:
				visibilityForModifier = true;
				break;
			default:
				visibilityForModifier = false;
				break;
			}
			// First modifier = element modifier
			if (i - startIndex < 1) {
				visibility = visibilityForModifier;
			}
			// First visible modifier = suggested element modifier
			if (visibilityForModifier) {
				firstVisible = modifier.getName().toUpperCase();
				break;
			}
		}
		return new TypeVisibility(visibility, firstVisible);
	}

	private boolean isPublicInternalVisible(Resource contextResource, final T element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			final TModule elementModule = N4JSResource.getModule(element.eResource());
			return elementModule == null || Strings.equal(contextModule.getVendorID(), elementModule.getVendorID());
		}
		return false;
	}

	private boolean isPrivateVisible(Resource contextResource, final T element) {
		return element.eResource() == contextResource;
		// TModule typeModule = EcoreUtil2.getContainerOfType(element, TModule.class);
		// return typeModule == null || typeModule == contextModule;
	}

	private boolean isProjectVisible(Resource contextResource, final T element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			if (contextModule != null) {
				final TModule elementModule = N4JSResource.getModule(element.eResource());
				return elementModule == null
						|| elementModule == contextModule
						|| ( //
						Strings.equal(contextModule.getProjectId(), elementModule.getProjectId())
								&& Strings.equal(contextModule.getVendorID(), elementModule.getVendorID()) //
						)
						|| isTestedProjectOf(contextModule, elementModule);
			}
		}
		return false;
	}

	/**
	 * Returns with {@code true} if the context module argument belongs to a {@link ProjectType#TEST test} project and
	 * any of its tested projects contains the element module argument.
	 *
	 * @param contextModule
	 *            the content module.
	 * @param elementModule
	 *            the element module.
	 * @return {@code true} if the element module's container project is the tested project of the context module.
	 *         Otherwise returns with {@code false}.
	 */
	public boolean isTestedProjectOf(final TModule contextModule, final TModule elementModule) {
		if (null == elementModule || null == contextModule || null == elementModule.eResource()
				|| null == contextModule.eResource()) {
			return false;
		}

		for (final IN4JSProject testedProject : getTestedProjects(contextModule.eResource().getURI())) {
			final URI testProjectLocation = testedProject.getLocation();
			if (null != testProjectLocation) {
				final Resource eResource = elementModule.eResource();
				if (null != eResource) {
					final URI resourceUri = eResource.getURI();
					final IN4JSProject elementProject = core.findProject(resourceUri).orNull();
					if (null != elementProject) {
						if (emptyIfNull(elementProject.getProjectId()).equals(testedProject.getProjectId())) {
							return true;
						}
					}
				}
			}

		}

		return false;
	}

	/**
	 * Returns with a collection of projects that are tested by the container project for the resource given with the
	 * unique resource URI.
	 *
	 * @param contextResourceUri
	 *            the URI of the context resource to retrieve its container project's host.
	 * @return a collection of tested projects. May be empty but never {@code null}.
	 */
	public Collection<IN4JSProject> getTestedProjects(final URI contextResourceUri) {
		if (null == contextResourceUri) {
			return emptyList();
		}

		final IN4JSProject contextProject = core.findProject(contextResourceUri).orNull();
		if (null == contextProject || !contextProject.exists()) {
			return emptyList();
		}

		return contextProject.getTestedProjects();
	}

	/**
	 * Encapsulates visibility information as well as the minimally needed access modifier to make the type visible.
	 */
	public static class TypeVisibility {
		/**
		 * Instatiates TypeVisbility with given visibility and empty suggestion
		 *
		 * @param visibility
		 *            true on visible
		 */
		public TypeVisibility(boolean visibility) {
			this(visibility, null);
		}

		/**
		 * Instantiates TypeVisibility with given visibility and suggestion
		 *
		 * @param visibility
		 *            true on visible
		 * @param suggestion
		 *            access modifier as String
		 */

		public TypeVisibility(boolean visibility, String suggestion) {
			this.visibility = visibility;
			this.accessModifierSuggestion = suggestion;
		}

		/**
		 * Visibility: true if visible
		 */
		final public boolean visibility;
		/**
		 * Access modifier needed to make the element visible.
		 */
		final public String accessModifierSuggestion;
	}

}
