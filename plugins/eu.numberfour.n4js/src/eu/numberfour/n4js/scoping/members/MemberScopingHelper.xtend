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
package eu.numberfour.n4js.scoping.members

import com.google.inject.Inject
import eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression
import eu.numberfour.n4js.scoping.accessModifiers.MemberVisibilityChecker
import eu.numberfour.n4js.scoping.accessModifiers.StaticWriteAccessFilterScope
import eu.numberfour.n4js.scoping.accessModifiers.VisibilityAwareMemberScope
import eu.numberfour.n4js.scoping.utils.CompositeScope
import eu.numberfour.n4js.scoping.utils.DynamicPseudoScope
import eu.numberfour.n4js.ts.scoping.builtin.BuiltInTypeScope
import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExprOrRef
import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExpression
import eu.numberfour.n4js.ts.typeRefs.FunctionTypeRef
import eu.numberfour.n4js.ts.typeRefs.IntersectionTypeExpression
import eu.numberfour.n4js.ts.typeRefs.ParameterizedTypeRef
import eu.numberfour.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import eu.numberfour.n4js.ts.typeRefs.ThisTypeRef
import eu.numberfour.n4js.ts.typeRefs.TypeRef
import eu.numberfour.n4js.ts.typeRefs.TypeTypeRef
import eu.numberfour.n4js.ts.typeRefs.UnionTypeExpression
import eu.numberfour.n4js.ts.typeRefs.UnknownTypeRef
import eu.numberfour.n4js.ts.types.ContainerType
import eu.numberfour.n4js.ts.types.PrimitiveType
import eu.numberfour.n4js.ts.types.TClass
import eu.numberfour.n4js.ts.types.TEnum
import eu.numberfour.n4js.ts.types.TMember
import eu.numberfour.n4js.ts.types.TN4Classifier
import eu.numberfour.n4js.ts.types.TObjectPrototype
import eu.numberfour.n4js.ts.types.TStructuralType
import eu.numberfour.n4js.ts.types.Type
import eu.numberfour.n4js.ts.types.TypeVariable
import eu.numberfour.n4js.ts.types.TypingStrategy
import eu.numberfour.n4js.ts.types.UndefinedType
import eu.numberfour.n4js.typesystem.N4JSTypeSystem
import eu.numberfour.n4js.typesystem.RuleEnvironmentExtensions
import eu.numberfour.n4js.typesystem.TypeSystemHelper
import eu.numberfour.n4js.utils.EcoreUtilN4
import eu.numberfour.n4js.validation.JavaScriptVariantHelper
import eu.numberfour.n4js.xtext.scoping.FilterWithErrorMarkerScope
import eu.numberfour.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes

/**
 */
class MemberScopingHelper {
	@Inject N4JSTypeSystem ts;
	@Inject TypeSystemHelper tsh;
	@Inject MemberScope.MemberScopeFactory memberScopeFactory
	@Inject private MemberVisibilityChecker memberVisibilityChecker
	@Inject	private JavaScriptVariantHelper jsVariantHelper; 


	/**
	 * Create a new member scope that filters using the given criteria (visibility, static access).
	 * <p>
	 * When choosing static scope, the {@code context} is inspected to determine read/write access
	 * but only if it's a {@link ParameterizedPropertyAccessExpression} or a {@code IndexedAccessExpression}.
	 * 
	 * @param receiverTypeRef
	 *               TypeRef for the value whose scope is of interest.
	 * @param context
	 *               AST node used for (a) obtaining context resource and (b) visibility checking.
	 * @param checkVisibility
	 *               if true, the member scope will be wrapped in a {@link VisibilityAwareMemberScope}; if
	 *               false, method {@link getPropertyTypeForNode(IScope,String)} will <b>never</b> return
	 *               {@link #INVISIBLE_MEMBER}.
	 * @param staticAccess
	 *               true: only static members are relevant; false: only non-static ones.
	 */
	public def IScope createMemberScopeFor(TypeRef receiverTypeRef, EObject context, boolean checkVisibility,
		boolean staticAccess) {
		return decoratedMemberScopeFor(receiverTypeRef,
			new MemberScopeRequest(receiverTypeRef, context, checkVisibility, staticAccess));
	}

	/**
	 * Creates member scope via #members and decorates it via #decorate.
	 */
	private def IScope decoratedMemberScopeFor(TypeRef typeRef, MemberScopeRequest memberScopeRequest) {
		var result = members(typeRef, memberScopeRequest);
		return result;
	}

	/**
	 * Called only be members functions to decorate returned scope.
	 */
	private def IScope decorate(IScope scope, MemberScopeRequest memberScopeRequest, TypeRef receiverTypeRef) {
		if (scope == IScope.NULLSCOPE) {
			return scope;
		}
		var decoratedScope = scope;
		if (memberScopeRequest.checkVisibility &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, VisibilityAwareMemberScope)) {
			decoratedScope = new VisibilityAwareMemberScope(decoratedScope, memberVisibilityChecker, receiverTypeRef,
				memberScopeRequest.context);
		}
		if (memberScopeRequest.staticAccess &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, StaticWriteAccessFilterScope)) {
			decoratedScope = new StaticWriteAccessFilterScope(decoratedScope, memberScopeRequest.context);
		}
		if (memberScopeRequest.checkVisibility &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, TypingStrategyAwareMemberScope)) {
			decoratedScope = new TypingStrategyAwareMemberScope(decoratedScope, receiverTypeRef,
				memberScopeRequest.context);
		}
		return decoratedScope;
	}

	/**
	 * Is there a member (visible or not) in the given scope matching the given (name, staticAccess) combination?
	 */
	public def boolean isNonExistentMember(IScope scope, String memberName, boolean staticAccess) {
		val descriptions = scope.getElements(QualifiedName.create(memberName))
		return descriptions.isEmpty
	}

	/**
	 * For the member given by (name, staticAccess) return the erroneous descriptions from the given scope.
	 * <p>
	 * Precondition: {@link #isNonExistentMember} has negative answer.
	 */
	public def Iterable<IEObjectDescriptionWithError> getErrorsForMember(IScope scope, String memberName,
		boolean staticAccess) {
		val descriptions = scope.getElements(QualifiedName.create(memberName))
		val errorsOrNulls = descriptions.map[d|IEObjectDescriptionWithError.getDescriptionWithError(d)]
		return errorsOrNulls.filterNull
	}

	/**
	 * Look up all non-erroneous {@link TMember} in the given scope having the given name.
	 */
	public def Iterable<TMember> findMembersForName(IScope scope, String memberName, boolean staticAccess) {
		val candidates = scope.getElements(QualifiedName.create(memberName)).filter [ description |
			!(IEObjectDescriptionWithError.isErrorDescription(description))
		]
		val proxysOrInstances = candidates.map[description|description.getEObjectOrProxy()]
		val tmembers = proxysOrInstances.filter [ proxyOrInstance |
			proxyOrInstance !== null && !proxyOrInstance.eIsProxy() && (proxyOrInstance instanceof TMember)
		]
		return tmembers.map[m|m as TMember].filter[m|m.static == staticAccess]
	}

	/**
	 * In case there's a single non-erroneous {@link TMember} in the given scope matching the given name, return it. Otherwise return null.
	 */
	public def TMember findUniqueMemberForName(IScope scope, String memberName, boolean staticAccess) {
		val candidates = findMembersForName(scope, memberName, staticAccess)
		if (candidates.size == 1) {
			return candidates.head
		}
		return null
	}

	private def dispatch IScope members(TypeRef type, MemberScopeRequest request) {
		return IScope.NULLSCOPE
	}

	private def dispatch IScope members(UnknownTypeRef type, MemberScopeRequest request) {
		return new DynamicPseudoScope()
	}

	private def dispatch IScope members(ParameterizedTypeRef ptr, MemberScopeRequest request) {
		val IScope result = membersOfType(ptr.declaredType, request);
		if (ptr.dynamic && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result.decorate(request, ptr))
		}
		return result.decorate(request, ptr)
	}

	private def dispatch IScope members(ParameterizedTypeRefStructural ptrs, MemberScopeRequest request) {
		val IScope result = membersOfType(ptrs.declaredType, request);
		if (ptrs.dynamic && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result.decorate(request, ptrs))
		}
		if (ptrs.structuralMembers.empty) {
			return result.decorate(request, ptrs)
		}
		val memberScopeRaw = if (ptrs.structuralType !== null) {
				memberScopeFactory.create(result, ptrs.structuralType, request.context, request.staticAccess);
			} else {
				// note: these are not the members of the defined type
				// however, we only scope locally, so that doesn't matter
				memberScopeFactory.create(result, ptrs.structuralMembers, request.context, request.staticAccess);
			}

		return decorate(memberScopeRaw, request, ptrs);
	}

	/**
	 * Note: N4JSScopeProvider already taking the upper bound before using this class (thus resolving ThisTypeRefs
	 * beforehand), so we will never enter this method from there; still provided to support uses from other code.
	 */
	private def dispatch IScope members(ThisTypeRef thisTypeRef, MemberScopeRequest request) {
		// taking the upper bound to "resolve" the ThisTypeRef:
		// this[C] --> C (ParameterizedTypeRef)
		// ~~this[C] with { number prop; } --> ~~C with { number prop; } (ParameterizedTypeRefStructural)
		val ub = ts.upperBound(RuleEnvironmentExtensions.newRuleEnvironment(request.context), thisTypeRef);

		if (!ub.failed) { // ThisTypeRef was resolved
			return members(ub.first, request);
		}

		// probably an unbound ThisTypeRef or some other error (reported elsewhere)
		return IScope.NULLSCOPE;
	}

	private def dispatch IScope members(TypeTypeRef ttr, MemberScopeRequest request) {
		val MemberScopeRequest staticRequest = request.enforceStatic;
		val G = RuleEnvironmentExtensions.newRuleEnvironment(request.context);
		val ctrStaticType = tsh.getStaticType(G, ttr);
		var IScope staticMembers = membersOfType(ctrStaticType, staticRequest) // staticAccess is always true in this case
		if (ctrStaticType instanceof TEnum) {
			// enums have their literals as static members
			staticMembers = Scopes.scopeFor(ctrStaticType.literals, staticMembers).decorate(request, ttr);
		}
		if (ttr.dynamic && !(staticMembers instanceof DynamicPseudoScope)) {
			staticMembers = new DynamicPseudoScope(staticMembers.decorate(staticRequest, ttr))
		}
		// in addition, we need instance members of either Function (in case of constructor{T}) or Object (for type{T})
		val MemberScopeRequest instanceRequest = request.enforceInstance;
		val builtInScope = BuiltInTypeScope.get(getResourceSet(ttr, request.context));
		val functionType = if (ttr.isConstructorRef) builtInScope.functionType else builtInScope.objectType;
		val IScope ftypeScope = membersOfType(functionType, instanceRequest);

		// order matters (shadowing!)
		val result = CompositeScope.create(
			staticMembers.decorate(staticRequest, ttr),
			ftypeScope.decorate(instanceRequest, ttr)
		);
		return result
	}

	private def dispatch IScope members(UnionTypeExpression uniontypeexp, MemberScopeRequest request) {

		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		val subScopes = uniontypeexp.typeRefs.map [ elementTypeRef |
			val scope = members(elementTypeRef, request);
			return scope;
		]

		switch (subScopes.size) { // only create union scope if really necessary, remember this optimization in test, since union{A} tests scope of A only!
			case 0: return IScope.NULLSCOPE
			case 1: return subScopes.get(0)
			default: return new UnionMemberScope(uniontypeexp, request.context, subScopes, ts)
		}
	}

	private def dispatch IScope members(IntersectionTypeExpression intersectiontypeexp, MemberScopeRequest request) {
//		if (intersectiontypeexp.typeRefs.isEmpty) {
		return IScope.NULLSCOPE;
//		}
	// TODO implement that (uncommented hack to have a stable situation)
//		val List<IScope> subScopes = intersectiontypeexp.typeRefs.map [ elementTypeRef |
//			val scope = members(elementTypeRef, request);
//			return scope;
//		]
//		return CompositeScope.create(subScopes);
	}

	private def dispatch IScope members(FunctionTypeRef ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request)
	}

	private def dispatch IScope members(FunctionTypeExpression ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request)
	}

	/** delegated from two methods above, to avoid catch-all of ParameterizedTypeRef for FuntionTypeRefs while dispatching */
	def private IScope membersOfFunctionTypeRef(FunctionTypeExprOrRef ftExpr, MemberScopeRequest request) {
		val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(ftExpr, request.context));
		val fType = builtInTypeScope.functionType
		val ret = membersOfType(fType, request)
		return ret.decorate(request, ftExpr);
	}

// TODO member computation should be extracted
	private def dispatch IScope membersOfType(Type type, MemberScopeRequest request) {
		if (type.eIsProxy) {
			return new DynamicPseudoScope()
		}
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		return IScope.NULLSCOPE
	}

	private def dispatch IScope membersOfType(UndefinedType type, MemberScopeRequest request) {
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		return IScope.NULLSCOPE
	}

	private def dispatch IScope membersOfType(Void type, MemberScopeRequest request) {
		return new DynamicPseudoScope()
	}

	/**
	 * Primitive types have no members, but they can be auto-boxed to their
	 * corresponding object type which then, transparently to the user, provide members.
	 */
	private def dispatch IScope membersOfType(PrimitiveType prim, MemberScopeRequest request) {
		val boxedType = prim.autoboxedType;
		return if(boxedType!==null) membersOfType(boxedType, request) else IScope.NULLSCOPE;
	}

	/**
	 * Creates member scope with parent containing members of implicit super types.
	 */
	private def dispatch IScope membersOfType(ContainerType<?> type, MemberScopeRequest request) {
		val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(type, request.context));
		var implicitSuperType = builtInTypeScope.objectType
		if (type instanceof TN4Classifier) {
			if (type.typingStrategy !== TypingStrategy.DEFAULT) {
				implicitSuperType = builtInTypeScope.objectType
			} else if (type instanceof TClass) {
				if (type.superClassRef === null || type.superClassRef.declaredType != builtInTypeScope.objectType) {
					implicitSuperType = builtInTypeScope.n4ObjectType;
				}
			} else { // TInterface
				implicitSuperType = builtInTypeScope.n4ObjectType;
			}
		}
		val implicitSuperTypeMemberScope = if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
				memberScopeFactory.create(new DynamicPseudoScope(), implicitSuperType, request.context,
					request.staticAccess);
			} else {
				memberScopeFactory.create(implicitSuperType, request.context, request.staticAccess);
			}
		return memberScopeFactory.create(implicitSuperTypeMemberScope, type, request.context, request.staticAccess);
	}

	/**
	 * Returns a scope of the literals, that is members such as name or value.
	 * That is, the instance members of an enumeration. The static members are made available
	 * in {@link #members(EnumTypeRef, EObject, boolean)}
	 */
	private def dispatch IScope membersOfType(TEnum enumeration, MemberScopeRequest request) {
		val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(enumeration, request.context));
		// IDE-1221 select built-in type depending on whether this enumeration is tagged string-based
		val TObjectPrototype specificEnumType = if (TypeSystemHelper.isStringBasedEnumeration(enumeration)) {
				builtInTypeScope.n4StringBasedEnumType
			} else {
				builtInTypeScope.n4EnumType
			};
		val enumScope = memberScopeFactory.create(specificEnumType, request.context, request.staticAccess);
		return enumScope;
	}

	private def dispatch IScope membersOfType(TypeVariable typeVar, MemberScopeRequest request) {
		val declUB = typeVar.declaredUpperBound;
		if (declUB!==null) {
			return members(declUB, request)
		} else {
			val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(typeVar, request.context));
			val anyType = builtInTypeScope.anyType
			return membersOfType(anyType, request);
		}

	}

	private def dispatch IScope membersOfType(TStructuralType structType, MemberScopeRequest request) {
		if (structType.ownedMembers.empty) {
			return IScope.NULLSCOPE
		}
		return memberScopeFactory.create(structType, request.context, request.staticAccess)
	}

	def private ResourceSet getResourceSet(EObject type, EObject context) {
		var result = EcoreUtilN4.getResourceSet(type, context);
		if (result === null)
			throw new IllegalStateException("type or context must be contained in a ResourceSet")
		return result;
	}
}
