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
package eu.numberfour.n4js.scoping.members;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import eu.numberfour.n4js.n4JS.extensions.ExpressionExtensions;
import eu.numberfour.n4js.ts.typeRefs.ComposedTypeRef;
import eu.numberfour.n4js.ts.typeRefs.TypeRefsFactory;
import eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage;
import eu.numberfour.n4js.ts.typeRefs.UnionTypeExpression;
import eu.numberfour.n4js.ts.typeRefs.UnknownTypeRef;
import eu.numberfour.n4js.ts.types.FieldAccessor;
import eu.numberfour.n4js.ts.types.TField;
import eu.numberfour.n4js.ts.types.TGetter;
import eu.numberfour.n4js.ts.types.TMember;
import eu.numberfour.n4js.ts.types.TypesFactory;
import eu.numberfour.n4js.ts.utils.TypeUtils;
import eu.numberfour.n4js.typesystem.N4JSTypeSystem;
import eu.numberfour.n4js.utils.EcoreUtilN4;
import eu.numberfour.n4js.xtext.scoping.IEObjectDescriptionWithError;
import it.xsemantics.runtime.RuleEnvironment;

/**
 * Scope implementation for ComposedTypeRefs, i.e. union types and intersection types.
 * <p>
 * This scope assumes that the sub-scopes are already filtered according to static access and visibility. Static access
 * can be modified by the sub-scope and checking it here again would lead to problems, e.g., in case of classifier type.
 * <p>
 * Note that there cannot be static access to a union or intersection type, since a definition of a composed type
 * actually is a reference.
 */
public class UnionMemberScope extends AbstractScope {

	private final UnionTypeExpression unionTypeExpression;
	private final IScope[] subScopes;
	private final EObject context;

	private final N4JSTypeSystem ts;
	private final boolean writeAccess;

	/**
	 * Creates union type scope, passed subScopes are expected to be fully configured (i.e., including required filters
	 * etc.)
	 */
	public UnionMemberScope(UnionTypeExpression unionTypeExpression, EObject context, List<IScope> subScopes,
			N4JSTypeSystem ts) {

		super(IScope.NULLSCOPE, false);

		this.unionTypeExpression = unionTypeExpression;
		this.subScopes = subScopes.toArray(new IScope[subScopes.size()]);
		this.ts = ts;
		this.context = context;
		this.writeAccess = ExpressionExtensions.isLeftHandSide(context);
	}

	/**
	 * Returns all elements of union. No erroneous descriptions (instances of IEObjectDescriptionWithError) will be
	 * considered here, as we assume this method to be called from content assist and we do not want to show wrong
	 * elements. These descriptions will be returned by {@link #getSingleElement(QualifiedName)} though to show errors
	 * in case of explicit references to these members.
	 */
	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {

		// collect all names from subScopes
		final Set<String> names = new HashSet<>();
		for (IScope currSubScope : subScopes) {
			try {
				for (IEObjectDescription currDesc : currSubScope.getAllElements()) {
					// omit erroneous bindings (they will be provided in getSingleLocalElement... though)
					if (!(currDesc instanceof IEObjectDescriptionWithError)) {
						String name = currDesc.getName().getLastSegment();
						names.add(name);
					}
				}
			} catch (UnsupportedOperationException e) {
				// according to API doc of IScope#getAllElements(), scopes are free to throw an
				// UnsupportedOperationException --> therefore we catch and ignore this here
			}
		}

		List<IEObjectDescription> descriptions = new ArrayList<>(names.size());
		for (String name : names) {
			IEObjectDescription description = getSingleLocalElementByName(QualifiedName.create(name));
			if (description != null && !(description instanceof IEObjectDescriptionWithError)) {
				descriptions.add(description);
			}
		}
		return descriptions;
	}

	/**
	 * Returns description for given element, name is assumed to consist of a single segment containing the simple name
	 * of the member. If no subScope contains a member with given name, null is returned. In other error cases (no or
	 * wrong access, mixed types etc.), {@link IEObjectDescriptionWithError}, and in particular
	 * {@link UnionMemberDescriptionWithError}, will be returned.
	 */
	@Override
	protected IEObjectDescription getSingleLocalElementByName(QualifiedName qualifiedName) {
		String name = qualifiedName.getLastSegment();
		TMember member = getOrCreateComposedMember(name);
		if (member == null) { // no such member, no need for "merging" descriptions, there won't be any
			return null;
		}

		if (isErrorPlaceholder(member)) {
			return createUnionMemberDescriptionWithErrors(EObjectDescription.create(member.getName(), member));
		}
		IEObjectDescription description = EObjectDescription.create(member.getName(), member);

		QualifiedName qn = QualifiedName.create(name);
		for (IScope currSubScope : subScopes) {
			IEObjectDescription subDescription = currSubScope.getSingleElement(qn);
			if (description == null || subDescription instanceof IEObjectDescriptionWithError) {
				return createUnionMemberDescriptionWithErrors(description);
			}
		}
		return description;
	}

	private IEObjectDescription createUnionMemberDescriptionWithErrors(IEObjectDescription result) {
		return new UnionMemberDescriptionWithError(result, unionTypeExpression, subScopes, writeAccess);
	}

	/**
	 * For members of a union, pseudo instances need to be created (since the "union member" is not one of the members
	 * of the sub-elements, but a new "merged" version). These pseudo instances are cached in the resource.
	 */
	private TMember getOrCreateComposedMember(String memberName) {
		// look up cache
		for (TMember currM : getCacheHolder(unionTypeExpression).getCachedComposedMembers()) {
			if (memberName.equals(currM.getName()) && hasCorrectAccess(currM, writeAccess)) {
				return currM;
			}
		}
		// not found, then create
		return createComposedMember(memberName);

	}

	/**
	 * Key method of entire scoping for union types. This creates a new TMember as a combination of all members of the
	 * given name in the union type's contained types. If those members cannot be combined into a single valid member,
	 * this method creates a dummy placeholder.
	 */
	private TMember createComposedMember(String memberName) {
		// check all subScopes for a member of the given name and
		// merge the properties of the existing members into 'composedMember'
		final ComposedMemberDescriptor composedMemberDescr = new ComposedMemberDescriptor(
				writeAccess, EcoreUtilN4.getResource(context, unionTypeExpression), ts);
		for (int idx = 0; idx < subScopes.length; idx++) {
			IScope subScope = subScopes[idx];
			final RuleEnvironment GwithSubstitutions = ts.createRuleEnvironmentForContext(
					unionTypeExpression.getTypeRefs().get(idx),
					EcoreUtilN4.getResource(context, unionTypeExpression));
			TMember member = findMemberInSubScope(subScope, memberName);
			composedMemberDescr.merge(GwithSubstitutions, member);
		}
		// produce result
		if (!composedMemberDescr.isEmpty()) {
			// at least one of the subScopes had an element of that name
			final TMember result;
			if (composedMemberDescr.isValid()) {
				// success case:
				// 1) ALL of the subScopes have an element for that name and
				// 2) they can be merged into a valid composed member
				result = composedMemberDescr.create(memberName);
			} else {
				// some of the subScopes do not have an element for that name OR
				// they do not form a valid composed member (e.g. they are of different kind)
				// -> produce a specific error message explaining the incompatibility
				// (this error placeholder will be wrapped with a UncommonMemberDescription
				// in #getSingleLocalElementByName(QualifiedName) above)
				result = createErrorPlaceholder(memberName);
			}
			// add composed member to ComposedTypeRef (without notifications to avoid cache-clear)
			final ComposedTypeRef cacheHolder = getCacheHolder(unionTypeExpression);
			EcoreUtilN4.doWithDeliver(false, () -> {
				cacheHolder.getCachedComposedMembers().add(result);
			}, cacheHolder);
			return result;
		} else {
			// none of the subScopes has an element of that name
			// -> produce the ordinary "Cannot resolve reference ..." error by returning 'null'
			return null;
		}
	}

	/**
	 * Cached composed members must be stored in a resource. However, due to copying during type variable substitution,
	 * we might be dealing with a dangling (i.e. not contained in a resource) copy of another ComposedTypeRef contained
	 * in the AST or type model. Therefore, follow the chain defined by property 'originalComposedTypeRef' until we find
	 * the first ComputedTypeRef that is contained in a resource.
	 * <p>
	 * See also Xsemantics rule 'substTypeVariablesInComposedTypeRef'.
	 */
	private ComposedTypeRef getCacheHolder(ComposedTypeRef ctr) {
		while (ctr.eResource() == null && ctr.getOriginalComposedTypeRef() != null)
			ctr = ctr.getOriginalComposedTypeRef();
		// if (ctr.eResource() == null) {
		// // error is necessary since EMF catches all exceptions in EcoreUtil#resolved
		// throw new AssertionError("cacheHolder must be contained in a resource");
		// }
		return ctr;
	}

	/**
	 * To avoid having to do all computation over and over in case no valid composed member can be built, we also create
	 * a member in the error case as a placeholder.
	 * <p>
	 * Note: we need to be able to store error place holders and/or successfully composed members for read- and
	 * write-access independently (i.e. we might have, for example, a valid composed member for read access but an error
	 * placeholder for write access); therefore we have to use getters/setters for error place holders.
	 */
	private TMember createErrorPlaceholder(String memberName) {
		if (writeAccess) {
			return TypeUtils.createTSetter(memberName, null, TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
		} else {
			final TGetter g = TypesFactory.eINSTANCE.createTGetter();
			g.setName(memberName);
			g.setDeclaredTypeRef(TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
			return g;
		}
	}

	/**
	 * Returns true iff 'obj' was created by method {@link #createErrorPlaceholder(String)}.
	 */
	private boolean isErrorPlaceholder(EObject obj) {
		return obj != null && !obj.eIsProxy() &&
				obj instanceof FieldAccessor &&
				TypeUtils.getMemberTypeRef((FieldAccessor) obj) instanceof UnknownTypeRef;
	}

	/**
	 * Searches for a member of the given name and for the given access in the sub-scope with index 'subScopeIdx'.
	 */
	private TMember findMemberInSubScope(IScope subScope, String name) {
		final IEObjectDescription currElem = subScope.getSingleElement(QualifiedName.create(name));
		if (currElem != null) {
			final EObject objOrProxy = currElem.getEObjectOrProxy();
			if (objOrProxy != null && !objOrProxy.eIsProxy() && objOrProxy instanceof TMember) {
				final TMember currM = (TMember) objOrProxy;
				if (hasCorrectAccess(currM, writeAccess)
						|| (currM instanceof TField && hasCorrectAccess(currM, !writeAccess))) {
					return currM;
				} else {
					return createErrorPlaceholder(name);
				}
			}
		}
		return null;
	}

	private static boolean hasCorrectAccess(TMember currM, boolean writeAccess) {
		return ((!writeAccess && currM.isReadable()) || (writeAccess && currM.isWriteable()));
	}

	/**
	 * Tells if the given member is a "composed member", i.e. if it is a virtual member created to represent the union
	 * of intersection of several other members in the context of a union or intersection type.
	 * <p>
	 * All code introducing special handling of such members should use this method, so that it will be easy to locate
	 * these places in case scoping of union/intersection types is modified.
	 */
	public static final boolean isComposedMember(TMember member) {
		return member.eContainer() instanceof ComposedTypeRef
				&& member.eContainingFeature() == TypeRefsPackage.eINSTANCE.getComposedTypeRef_CachedComposedMembers();
	}

	/**
	 * This clears all cached TMembers in EMF property {@link ComposedTypeRef#getCachedComposedMembers()
	 * cachedComposedMembers()} in astElement and the entire AST below astElement.
	 * <p>
	 * IMPORTANT: this must be called whenever parts of the AST are being reused (when doing partial parsing).
	 * <p>
	 * TODO: consider alternative of moving the cache to TModule OR improve how this is called from N4JSLinker
	 */
	public static final void clearCachedComposedMembers(EObject astElement) {
		if (astElement instanceof ComposedTypeRef)
			astElement.eUnset(TypeRefsPackage.Literals.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS);
		final TreeIterator<EObject> iter = astElement.eAllContents();
		while (iter.hasNext()) {
			final EObject currObj = iter.next();
			if (currObj instanceof ComposedTypeRef) {
				// clear the cache of composed members
				currObj.eUnset(TypeRefsPackage.Literals.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS);
				iter.prune();
			}
		}
	}
}
