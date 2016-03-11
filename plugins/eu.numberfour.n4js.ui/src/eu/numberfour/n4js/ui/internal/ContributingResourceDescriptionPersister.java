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
package eu.numberfour.n4js.ui.internal;

import static com.google.common.collect.FluentIterable.from;
import static eu.numberfour.n4js.ui.resource.IResourceDescriptionPersisterContribution.CLAZZ_PROPERTY_NAME;
import static eu.numberfour.n4js.ui.resource.IResourceDescriptionPersisterContribution.EXTENSION_POINT_ID;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.builder.builderState.EMFBasedPersister;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Singleton;

import eu.numberfour.n4js.ui.building.ResourceDescriptionWithoutModuleUserData;
import eu.numberfour.n4js.ui.resource.IResourceDescriptionPersisterContribution;

/**
 * Persisted state provider that is responsible for notifying all registered
 * {@link IResourceDescriptionPersisterContribution contribution} instances before the recovery build is being triggered
 * due to awkward IDE shutdown or crash.
 */
@SuppressWarnings("restriction")
@Singleton
public class ContributingResourceDescriptionPersister extends EMFBasedPersister {

	private static final Logger LOGGER = Logger.getLogger(ContributingResourceDescriptionPersister.class);

	private final Supplier<Iterable<IResourceDescriptionPersisterContribution>> contributionsSupplier = Suppliers
			.memoize(() -> getContributions());

	private final AtomicBoolean requiresRecoveryBuild = new AtomicBoolean(false);

	/**
	 * Unlike in the {@link EMFBasedPersister} superclass this class not just saves all {@link IResourceDescription
	 * resource description} instances that are an instance of {@link EObject} but checks whether any description
	 * instances of the argument is an instance of {@link ResourceDescriptionWithoutModuleUserData resource description
	 * without TModule user data}. If so, checks whether the delegate is an instance of {@link EObject} and saves that
	 * description as well. This is required because the customized resource description (which is not a subclass of
	 * {@link EObject}) gets into the {@link IResourceDescriptions Xtext index} in the builder state.
	 *
	 * @see ResourceDescriptionWithoutModuleUserData
	 */
	@Override
	public void saveToResource(final Resource res, final Iterable<IResourceDescription> descriptions) {
		super.saveToResource(res, descriptions);
		for (final IResourceDescription description : descriptions) {
			if (description instanceof EObject) {
				res.getContents().add((EObject) description);
				/**
				 * This case was introduced to supply a pragmatic solution for the incorrect Xtext index persistence
				 * issue due to the customized {@link ResourceDescriptionWithoutModuleUserData} that gets into the
				 * {@link IBuilderState builder state's} index although they do not supposed to show up there. This
				 * happens only for index elements for external libraries.
				 *
				 * IDEBUG-855 and IDEBUG-857
				 */
			} else if (description instanceof ResourceDescriptionWithoutModuleUserData) {
				final IResourceDescription delegate = ((ResourceDescriptionWithoutModuleUserData) description)
						.getDelegate();
				if (delegate instanceof EObject) {
					res.getContents().add((EObject) delegate);
				}
			}
		}
	}

	@Override
	protected void scheduleRecoveryBuild() {
		requiresRecoveryBuild.compareAndSet(false, true);
		super.scheduleRecoveryBuild();
	}

	/**
	 * Triggers a {@link IResourceDescriptionPersisterContribution#scheduleRecoveryBuild()} on all available
	 * contributions.
	 */
	public void scheduleRecoveryBuildOnContributions() {
		for (final IResourceDescriptionPersisterContribution contribution : contributionsSupplier.get()) {
			contribution.scheduleRecoveryBuild();
		}
	}

	/**
	 * Returns with {@code true} if recovery build was required due to missing or corrupted Xtext index.
	 *
	 * @return {@code true} if recovery build if recovery build was triggered, otherwise {@code false}.
	 */
	public boolean isRecoveryBuildRequired() {
		return requiresRecoveryBuild.get();
	}

	private Iterable<IResourceDescriptionPersisterContribution> getContributions() {
		return from(Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)))
				.transform(config -> createInstance(config));
	}

	private IResourceDescriptionPersisterContribution createInstance(final IConfigurationElement config) {
		try {
			final Object extension = config.createExecutableExtension(CLAZZ_PROPERTY_NAME);
			if (extension instanceof IResourceDescriptionPersisterContribution) {
				IResourceDescriptionPersisterContribution contribution = (IResourceDescriptionPersisterContribution) extension;
				contribution.getInjector().injectMembers(contribution);
				return contribution;
			}
			final String message = "Expected persister contribution type. Was: " + extension;
			LOGGER.error(message);
			throw new IllegalStateException(message);
		} catch (final CoreException e) {
			final String message = "Error while instantiating contribution.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

}
