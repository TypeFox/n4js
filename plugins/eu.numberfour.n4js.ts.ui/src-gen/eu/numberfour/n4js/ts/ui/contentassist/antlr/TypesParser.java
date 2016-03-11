/*
 * generated by Xtext
 */
package eu.numberfour.n4js.ts.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import eu.numberfour.n4js.ts.services.TypesGrammarAccess;

public class TypesParser extends AbstractContentAssistParser {
	
	@Inject
	private TypesGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected eu.numberfour.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser createParser() {
		eu.numberfour.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser result = new eu.numberfour.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getTAnnotationArgumentAccess().getAlternatives(), "rule__TAnnotationArgument__Alternatives");
					put(grammarAccess.getTypeAccess().getAlternatives(), "rule__Type__Alternatives");
					put(grammarAccess.getNullModifierTokenAccess().getAlternatives(), "rule__NullModifierToken__Alternatives");
					put(grammarAccess.getUndefModifierTokenAccess().getAlternatives(), "rule__UndefModifierToken__Alternatives");
					put(grammarAccess.getTypeReferenceNameAccess().getAlternatives(), "rule__TypeReferenceName__Alternatives");
					put(grammarAccess.getTypesIdentifierAccess().getAlternatives(), "rule__TypesIdentifier__Alternatives");
					put(grammarAccess.getTypesComputedPropertyNameAccess().getAlternatives_1(), "rule__TypesComputedPropertyName__Alternatives_1");
					put(grammarAccess.getTMemberAccess().getAlternatives(), "rule__TMember__Alternatives");
					put(grammarAccess.getTMethodAccess().getAlternatives_0_0_1(), "rule__TMethod__Alternatives_0_0_1");
					put(grammarAccess.getTMethodAccess().getAlternatives_0_0_3(), "rule__TMethod__Alternatives_0_0_3");
					put(grammarAccess.getTFieldAccess().getAlternatives_1(), "rule__TField__Alternatives_1");
					put(grammarAccess.getTFieldAccess().getAlternatives_2(), "rule__TField__Alternatives_2");
					put(grammarAccess.getTGetterAccess().getAlternatives_0_0_2(), "rule__TGetter__Alternatives_0_0_2");
					put(grammarAccess.getTGetterAccess().getAlternatives_0_0_4(), "rule__TGetter__Alternatives_0_0_4");
					put(grammarAccess.getTSetterAccess().getAlternatives_0_0_2(), "rule__TSetter__Alternatives_0_0_2");
					put(grammarAccess.getTSetterAccess().getAlternatives_0_0_4(), "rule__TSetter__Alternatives_0_0_4");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives(), "rule__TypeRefWithoutModifiers__Alternatives");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives_0_0(), "rule__TypeRefWithoutModifiers__Alternatives_0_0");
					put(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getAlternatives(), "rule__TypeRefFunctionTypeExpression__Alternatives");
					put(grammarAccess.getTypeRefForCastAccess().getAlternatives(), "rule__TypeRefForCast__Alternatives");
					put(grammarAccess.getTypeRefInClassifierTypeAccess().getAlternatives(), "rule__TypeRefInClassifierType__Alternatives");
					put(grammarAccess.getThisTypeRefAccess().getAlternatives(), "rule__ThisTypeRef__Alternatives");
					put(grammarAccess.getParameterizedTypeRefAccess().getAlternatives(), "rule__ParameterizedTypeRef__Alternatives");
					put(grammarAccess.getTStructMemberListAccess().getAlternatives_1_1(), "rule__TStructMemberList__Alternatives_1_1");
					put(grammarAccess.getTStructMemberAccess().getAlternatives(), "rule__TStructMember__Alternatives");
					put(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getAlternatives_1(), "rule__TypingStrategyUseSiteOperator__Alternatives_1");
					put(grammarAccess.getTypeArgumentAccess().getAlternatives(), "rule__TypeArgument__Alternatives");
					put(grammarAccess.getWildcardAccess().getAlternatives_1(), "rule__Wildcard__Alternatives_1");
					put(grammarAccess.getTIdentifierAccess().getAlternatives(), "rule__TIdentifier__Alternatives");
					put(grammarAccess.getTypeAccessModifierAccess().getAlternatives(), "rule__TypeAccessModifier__Alternatives");
					put(grammarAccess.getMemberAccessModifierAccess().getAlternatives(), "rule__MemberAccessModifier__Alternatives");
					put(grammarAccess.getTAnnotationAccess().getGroup(), "rule__TAnnotation__Group__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_0(), "rule__TAnnotation__Group_0__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_0_0(), "rule__TAnnotation__Group_0_0__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1(), "rule__TAnnotation__Group_1__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1_1(), "rule__TAnnotation__Group_1_1__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1_1_1(), "rule__TAnnotation__Group_1_1_1__0");
					put(grammarAccess.getTypeRefAccess().getGroup(), "rule__TypeRef__Group__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup(), "rule__PrimitiveType__Group__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_2(), "rule__PrimitiveType__Group_2__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_3(), "rule__PrimitiveType__Group_3__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_5(), "rule__PrimitiveType__Group_5__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_6(), "rule__PrimitiveType__Group_6__0");
					put(grammarAccess.getTypeReferenceNameAccess().getGroup_5(), "rule__TypeReferenceName__Group_5__0");
					put(grammarAccess.getTypeReferenceNameAccess().getGroup_5_1(), "rule__TypeReferenceName__Group_5_1__0");
					put(grammarAccess.getAnyTypeAccess().getGroup(), "rule__AnyType__Group__0");
					put(grammarAccess.getVoidTypeAccess().getGroup(), "rule__VoidType__Group__0");
					put(grammarAccess.getUndefinedTypeAccess().getGroup(), "rule__UndefinedType__Group__0");
					put(grammarAccess.getNullTypeAccess().getGroup(), "rule__NullType__Group__0");
					put(grammarAccess.getTypesComputedPropertyNameAccess().getGroup(), "rule__TypesComputedPropertyName__Group__0");
					put(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getGroup(), "rule__TypesSymbolLiteralComputedName__Group__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup(), "rule__TObjectPrototype__Group__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_5(), "rule__TObjectPrototype__Group_5__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_5_2(), "rule__TObjectPrototype__Group_5_2__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_6(), "rule__TObjectPrototype__Group_6__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_7(), "rule__TObjectPrototype__Group_7__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_11(), "rule__TObjectPrototype__Group_11__0");
					put(grammarAccess.getVirtualBaseTypeAccess().getGroup(), "rule__VirtualBaseType__Group__0");
					put(grammarAccess.getTClassAccess().getGroup(), "rule__TClass__Group__0");
					put(grammarAccess.getTClassAccess().getGroup_7(), "rule__TClass__Group_7__0");
					put(grammarAccess.getTClassAccess().getGroup_7_2(), "rule__TClass__Group_7_2__0");
					put(grammarAccess.getTClassAccess().getGroup_8(), "rule__TClass__Group_8__0");
					put(grammarAccess.getTClassAccess().getGroup_9(), "rule__TClass__Group_9__0");
					put(grammarAccess.getTClassAccess().getGroup_9_2(), "rule__TClass__Group_9_2__0");
					put(grammarAccess.getTClassAccess().getGroup_13(), "rule__TClass__Group_13__0");
					put(grammarAccess.getTInterfaceAccess().getGroup(), "rule__TInterface__Group__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_5(), "rule__TInterface__Group_5__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_5_2(), "rule__TInterface__Group_5_2__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_6(), "rule__TInterface__Group_6__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_6_2(), "rule__TInterface__Group_6_2__0");
					put(grammarAccess.getCallableCtorAccess().getGroup(), "rule__CallableCtor__Group__0");
					put(grammarAccess.getCallableCtorAccess().getGroup_2(), "rule__CallableCtor__Group_2__0");
					put(grammarAccess.getCallableCtorAccess().getGroup_2_1(), "rule__CallableCtor__Group_2_1__0");
					put(grammarAccess.getCallableCtorAccess().getGroup_4(), "rule__CallableCtor__Group_4__0");
					put(grammarAccess.getTMethodAccess().getGroup(), "rule__TMethod__Group__0");
					put(grammarAccess.getTMethodAccess().getGroup_0(), "rule__TMethod__Group_0__0");
					put(grammarAccess.getTMethodAccess().getGroup_0_0(), "rule__TMethod__Group_0_0__0");
					put(grammarAccess.getTMethodAccess().getGroup_0_0_2(), "rule__TMethod__Group_0_0_2__0");
					put(grammarAccess.getTMethodAccess().getGroup_0_0_2_2(), "rule__TMethod__Group_0_0_2_2__0");
					put(grammarAccess.getTMethodAccess().getGroup_1(), "rule__TMethod__Group_1__0");
					put(grammarAccess.getTMethodAccess().getGroup_1_1(), "rule__TMethod__Group_1_1__0");
					put(grammarAccess.getTFieldAccess().getGroup(), "rule__TField__Group__0");
					put(grammarAccess.getTGetterAccess().getGroup(), "rule__TGetter__Group__0");
					put(grammarAccess.getTGetterAccess().getGroup_0(), "rule__TGetter__Group_0__0");
					put(grammarAccess.getTGetterAccess().getGroup_0_0(), "rule__TGetter__Group_0_0__0");
					put(grammarAccess.getTSetterAccess().getGroup(), "rule__TSetter__Group__0");
					put(grammarAccess.getTSetterAccess().getGroup_0(), "rule__TSetter__Group_0__0");
					put(grammarAccess.getTSetterAccess().getGroup_0_0(), "rule__TSetter__Group_0_0__0");
					put(grammarAccess.getTFunctionAccess().getGroup(), "rule__TFunction__Group__0");
					put(grammarAccess.getTFunctionAccess().getGroup_3(), "rule__TFunction__Group_3__0");
					put(grammarAccess.getTFunctionAccess().getGroup_3_2(), "rule__TFunction__Group_3_2__0");
					put(grammarAccess.getTFunctionAccess().getGroup_6(), "rule__TFunction__Group_6__0");
					put(grammarAccess.getTFunctionAccess().getGroup_6_1(), "rule__TFunction__Group_6_1__0");
					put(grammarAccess.getTEnumAccess().getGroup(), "rule__TEnum__Group__0");
					put(grammarAccess.getTEnumAccess().getGroup_6(), "rule__TEnum__Group_6__0");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getGroup_0(), "rule__TypeRefWithoutModifiers__Group_0__0");
					put(grammarAccess.getThisTypeRefNominalAccess().getGroup(), "rule__ThisTypeRefNominal__Group__0");
					put(grammarAccess.getThisTypeRefStructuralAccess().getGroup(), "rule__ThisTypeRefStructural__Group__0");
					put(grammarAccess.getThisTypeRefStructuralAccess().getGroup_2(), "rule__ThisTypeRefStructural__Group_2__0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getGroup(), "rule__FunctionTypeExpression__Group__0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getGroup_2(), "rule__FunctionTypeExpression__Group_2__0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getGroup_4(), "rule__FunctionTypeExpression__Group_4__0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getGroup_4_2(), "rule__FunctionTypeExpression__Group_4_2__0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getGroup_8(), "rule__FunctionTypeExpression__Group_8__0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup(), "rule__TAnonymousFormalParameterList__Group__0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup_1(), "rule__TAnonymousFormalParameterList__Group_1__0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getGroup(), "rule__TAnonymousFormalParameter__Group__0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1(), "rule__TAnonymousFormalParameter__Group_1__0");
					put(grammarAccess.getTFormalParameterAccess().getGroup(), "rule__TFormalParameter__Group__0");
					put(grammarAccess.getUnionTypeExpressionAccess().getGroup(), "rule__UnionTypeExpression__Group__0");
					put(grammarAccess.getUnionTypeExpressionAccess().getGroup_4(), "rule__UnionTypeExpression__Group_4__0");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getGroup(), "rule__IntersectionTypeExpression__Group__0");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getGroup_4(), "rule__IntersectionTypeExpression__Group_4__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup(), "rule__ParameterizedTypeRefStructural__Group__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_2(), "rule__ParameterizedTypeRefStructural__Group_2__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_2_2(), "rule__ParameterizedTypeRefStructural__Group_2_2__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_3(), "rule__ParameterizedTypeRefStructural__Group_3__0");
					put(grammarAccess.getTStructMemberListAccess().getGroup(), "rule__TStructMemberList__Group__0");
					put(grammarAccess.getTStructMemberListAccess().getGroup_1(), "rule__TStructMemberList__Group_1__0");
					put(grammarAccess.getTStructMethodAccess().getGroup(), "rule__TStructMethod__Group__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0(), "rule__TStructMethod__Group_0__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0_0(), "rule__TStructMethod__Group_0_0__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0_0_1(), "rule__TStructMethod__Group_0_0_1__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0_0_1_2(), "rule__TStructMethod__Group_0_0_1_2__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_3(), "rule__TStructMethod__Group_3__0");
					put(grammarAccess.getTStructFieldAccess().getGroup(), "rule__TStructField__Group__0");
					put(grammarAccess.getTStructFieldAccess().getGroup_1(), "rule__TStructField__Group_1__0");
					put(grammarAccess.getTStructGetterAccess().getGroup(), "rule__TStructGetter__Group__0");
					put(grammarAccess.getTStructGetterAccess().getGroup_0(), "rule__TStructGetter__Group_0__0");
					put(grammarAccess.getTStructGetterAccess().getGroup_0_0(), "rule__TStructGetter__Group_0_0__0");
					put(grammarAccess.getTStructGetterAccess().getGroup_3(), "rule__TStructGetter__Group_3__0");
					put(grammarAccess.getTStructSetterAccess().getGroup(), "rule__TStructSetter__Group__0");
					put(grammarAccess.getTStructSetterAccess().getGroup_0(), "rule__TStructSetter__Group_0__0");
					put(grammarAccess.getTStructSetterAccess().getGroup_0_0(), "rule__TStructSetter__Group_0_0__0");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup(), "rule__ParameterizedTypeRefNominal__Group__0");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup_1(), "rule__ParameterizedTypeRefNominal__Group_1__0");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup_1_2(), "rule__ParameterizedTypeRefNominal__Group_1_2__0");
					put(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getGroup(), "rule__TypingStrategyUseSiteOperator__Group__0");
					put(grammarAccess.getConstructorTypeRefAccess().getGroup(), "rule__ConstructorTypeRef__Group__0");
					put(grammarAccess.getClassifierTypeRefAccess().getGroup(), "rule__ClassifierTypeRef__Group__0");
					put(grammarAccess.getWildcardAccess().getGroup(), "rule__Wildcard__Group__0");
					put(grammarAccess.getWildcardAccess().getGroup_0(), "rule__Wildcard__Group_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_0_0(), "rule__Wildcard__Group_0_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_1_0(), "rule__Wildcard__Group_1_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_1_1(), "rule__Wildcard__Group_1_1__0");
					put(grammarAccess.getTypeVariableAccess().getGroup(), "rule__TypeVariable__Group__0");
					put(grammarAccess.getTypeVariableAccess().getGroup_1(), "rule__TypeVariable__Group_1__0");
					put(grammarAccess.getTypeVariableAccess().getGroup_1_2(), "rule__TypeVariable__Group_1_2__0");
					put(grammarAccess.getTypeDefsAccess().getTypesAssignment(), "rule__TypeDefs__TypesAssignment");
					put(grammarAccess.getTAnnotationAccess().getNameAssignment_0_0_1(), "rule__TAnnotation__NameAssignment_0_0_1");
					put(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_0(), "rule__TAnnotation__ArgsAssignment_1_1_0");
					put(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_1_1(), "rule__TAnnotation__ArgsAssignment_1_1_1_1");
					put(grammarAccess.getTAnnotationStringArgumentAccess().getValueAssignment(), "rule__TAnnotationStringArgument__ValueAssignment");
					put(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefAssignment(), "rule__TAnnotationTypeRefArgument__TypeRefAssignment");
					put(grammarAccess.getTypeRefAccess().getUndefModifierAssignment_1(), "rule__TypeRef__UndefModifierAssignment_1");
					put(grammarAccess.getTypeRefAccess().getNullModifierAssignment_2(), "rule__TypeRef__NullModifierAssignment_2");
					put(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1(), "rule__PrimitiveType__NameAssignment_1");
					put(grammarAccess.getPrimitiveTypeAccess().getTypeVarsAssignment_2_1(), "rule__PrimitiveType__TypeVarsAssignment_2_1");
					put(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeAssignment_3_1(), "rule__PrimitiveType__DeclaredElementTypeAssignment_3_1");
					put(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeAssignment_5_1(), "rule__PrimitiveType__AutoboxedTypeAssignment_5_1");
					put(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatibleAssignment_6_1(), "rule__PrimitiveType__AssignmentCompatibleAssignment_6_1");
					put(grammarAccess.getAnyTypeAccess().getNameAssignment_1(), "rule__AnyType__NameAssignment_1");
					put(grammarAccess.getVoidTypeAccess().getNameAssignment_1(), "rule__VoidType__NameAssignment_1");
					put(grammarAccess.getUndefinedTypeAccess().getNameAssignment_1(), "rule__UndefinedType__NameAssignment_1");
					put(grammarAccess.getNullTypeAccess().getNameAssignment_1(), "rule__NullType__NameAssignment_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TObjectPrototype__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TObjectPrototype__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalAssignment_2(), "rule__TObjectPrototype__DeclaredFinalAssignment_2");
					put(grammarAccess.getTObjectPrototypeAccess().getNameAssignment_4(), "rule__TObjectPrototype__NameAssignment_4");
					put(grammarAccess.getTObjectPrototypeAccess().getTypeVarsAssignment_5_1(), "rule__TObjectPrototype__TypeVarsAssignment_5_1");
					put(grammarAccess.getTObjectPrototypeAccess().getTypeVarsAssignment_5_2_1(), "rule__TObjectPrototype__TypeVarsAssignment_5_2_1");
					put(grammarAccess.getTObjectPrototypeAccess().getSuperTypeAssignment_6_1(), "rule__TObjectPrototype__SuperTypeAssignment_6_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeAssignment_7_1(), "rule__TObjectPrototype__DeclaredElementTypeAssignment_7_1");
					put(grammarAccess.getTObjectPrototypeAccess().getAnnotationsAssignment_8(), "rule__TObjectPrototype__AnnotationsAssignment_8");
					put(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_10(), "rule__TObjectPrototype__OwnedMembersAssignment_10");
					put(grammarAccess.getTObjectPrototypeAccess().getCallableCtorAssignment_11_0(), "rule__TObjectPrototype__CallableCtorAssignment_11_0");
					put(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_11_1(), "rule__TObjectPrototype__OwnedMembersAssignment_11_1");
					put(grammarAccess.getVirtualBaseTypeAccess().getNameAssignment_2(), "rule__VirtualBaseType__NameAssignment_2");
					put(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersAssignment_4(), "rule__VirtualBaseType__OwnedMembersAssignment_4");
					put(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TClass__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TClass__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTClassAccess().getDeclaredAbstractAssignment_2(), "rule__TClass__DeclaredAbstractAssignment_2");
					put(grammarAccess.getTClassAccess().getDeclaredFinalAssignment_3(), "rule__TClass__DeclaredFinalAssignment_3");
					put(grammarAccess.getTClassAccess().getTypingStrategyAssignment_5(), "rule__TClass__TypingStrategyAssignment_5");
					put(grammarAccess.getTClassAccess().getNameAssignment_6(), "rule__TClass__NameAssignment_6");
					put(grammarAccess.getTClassAccess().getTypeVarsAssignment_7_1(), "rule__TClass__TypeVarsAssignment_7_1");
					put(grammarAccess.getTClassAccess().getTypeVarsAssignment_7_2_1(), "rule__TClass__TypeVarsAssignment_7_2_1");
					put(grammarAccess.getTClassAccess().getSuperClassRefAssignment_8_1(), "rule__TClass__SuperClassRefAssignment_8_1");
					put(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_9_1(), "rule__TClass__ImplementedInterfaceRefsAssignment_9_1");
					put(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_9_2_1(), "rule__TClass__ImplementedInterfaceRefsAssignment_9_2_1");
					put(grammarAccess.getTClassAccess().getAnnotationsAssignment_10(), "rule__TClass__AnnotationsAssignment_10");
					put(grammarAccess.getTClassAccess().getOwnedMembersAssignment_12(), "rule__TClass__OwnedMembersAssignment_12");
					put(grammarAccess.getTClassAccess().getCallableCtorAssignment_13_0(), "rule__TClass__CallableCtorAssignment_13_0");
					put(grammarAccess.getTClassAccess().getOwnedMembersAssignment_13_1(), "rule__TClass__OwnedMembersAssignment_13_1");
					put(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TInterface__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TInterface__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTInterfaceAccess().getTypingStrategyAssignment_3(), "rule__TInterface__TypingStrategyAssignment_3");
					put(grammarAccess.getTInterfaceAccess().getNameAssignment_4(), "rule__TInterface__NameAssignment_4");
					put(grammarAccess.getTInterfaceAccess().getTypeVarsAssignment_5_1(), "rule__TInterface__TypeVarsAssignment_5_1");
					put(grammarAccess.getTInterfaceAccess().getTypeVarsAssignment_5_2_1(), "rule__TInterface__TypeVarsAssignment_5_2_1");
					put(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_6_1(), "rule__TInterface__SuperInterfaceRefsAssignment_6_1");
					put(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_6_2_1(), "rule__TInterface__SuperInterfaceRefsAssignment_6_2_1");
					put(grammarAccess.getTInterfaceAccess().getAnnotationsAssignment_7(), "rule__TInterface__AnnotationsAssignment_7");
					put(grammarAccess.getTInterfaceAccess().getOwnedMembersAssignment_9(), "rule__TInterface__OwnedMembersAssignment_9");
					put(grammarAccess.getCallableCtorAccess().getFparsAssignment_2_0(), "rule__CallableCtor__FparsAssignment_2_0");
					put(grammarAccess.getCallableCtorAccess().getFparsAssignment_2_1_1(), "rule__CallableCtor__FparsAssignment_2_1_1");
					put(grammarAccess.getCallableCtorAccess().getReturnTypeRefAssignment_4_1(), "rule__CallableCtor__ReturnTypeRefAssignment_4_1");
					put(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierAssignment_0_0_0(), "rule__TMethod__DeclaredMemberAccessModifierAssignment_0_0_0");
					put(grammarAccess.getTMethodAccess().getDeclaredStaticAssignment_0_0_1_0(), "rule__TMethod__DeclaredStaticAssignment_0_0_1_0");
					put(grammarAccess.getTMethodAccess().getDeclaredAbstractAssignment_0_0_1_1(), "rule__TMethod__DeclaredAbstractAssignment_0_0_1_1");
					put(grammarAccess.getTMethodAccess().getTypeVarsAssignment_0_0_2_1(), "rule__TMethod__TypeVarsAssignment_0_0_2_1");
					put(grammarAccess.getTMethodAccess().getTypeVarsAssignment_0_0_2_2_1(), "rule__TMethod__TypeVarsAssignment_0_0_2_2_1");
					put(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_0(), "rule__TMethod__NameAssignment_0_0_3_0");
					put(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_1(), "rule__TMethod__NameAssignment_0_0_3_1");
					put(grammarAccess.getTMethodAccess().getFparsAssignment_1_0(), "rule__TMethod__FparsAssignment_1_0");
					put(grammarAccess.getTMethodAccess().getFparsAssignment_1_1_1(), "rule__TMethod__FparsAssignment_1_1_1");
					put(grammarAccess.getTMethodAccess().getReturnTypeRefAssignment_4(), "rule__TMethod__ReturnTypeRefAssignment_4");
					put(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierAssignment_0(), "rule__TField__DeclaredMemberAccessModifierAssignment_0");
					put(grammarAccess.getTFieldAccess().getDeclaredStaticAssignment_1_0(), "rule__TField__DeclaredStaticAssignment_1_0");
					put(grammarAccess.getTFieldAccess().getConstAssignment_1_1(), "rule__TField__ConstAssignment_1_1");
					put(grammarAccess.getTFieldAccess().getDeclaredFinalAssignment_1_2(), "rule__TField__DeclaredFinalAssignment_1_2");
					put(grammarAccess.getTFieldAccess().getNameAssignment_2_0(), "rule__TField__NameAssignment_2_0");
					put(grammarAccess.getTFieldAccess().getNameAssignment_2_1(), "rule__TField__NameAssignment_2_1");
					put(grammarAccess.getTFieldAccess().getTypeRefAssignment_4(), "rule__TField__TypeRefAssignment_4");
					put(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_1(), "rule__TGetter__DeclaredMemberAccessModifierAssignment_0_0_1");
					put(grammarAccess.getTGetterAccess().getDeclaredAbstractAssignment_0_0_2_0(), "rule__TGetter__DeclaredAbstractAssignment_0_0_2_0");
					put(grammarAccess.getTGetterAccess().getDeclaredStaticAssignment_0_0_2_1(), "rule__TGetter__DeclaredStaticAssignment_0_0_2_1");
					put(grammarAccess.getTGetterAccess().getNameAssignment_0_0_4_0(), "rule__TGetter__NameAssignment_0_0_4_0");
					put(grammarAccess.getTGetterAccess().getNameAssignment_0_0_4_1(), "rule__TGetter__NameAssignment_0_0_4_1");
					put(grammarAccess.getTGetterAccess().getDeclaredTypeRefAssignment_4(), "rule__TGetter__DeclaredTypeRefAssignment_4");
					put(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_1(), "rule__TSetter__DeclaredMemberAccessModifierAssignment_0_0_1");
					put(grammarAccess.getTSetterAccess().getDeclaredAbstractAssignment_0_0_2_0(), "rule__TSetter__DeclaredAbstractAssignment_0_0_2_0");
					put(grammarAccess.getTSetterAccess().getDeclaredStaticAssignment_0_0_2_1(), "rule__TSetter__DeclaredStaticAssignment_0_0_2_1");
					put(grammarAccess.getTSetterAccess().getNameAssignment_0_0_4_0(), "rule__TSetter__NameAssignment_0_0_4_0");
					put(grammarAccess.getTSetterAccess().getNameAssignment_0_0_4_1(), "rule__TSetter__NameAssignment_0_0_4_1");
					put(grammarAccess.getTSetterAccess().getFparAssignment_2(), "rule__TSetter__FparAssignment_2");
					put(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TFunction__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TFunction__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTFunctionAccess().getTypeVarsAssignment_3_1(), "rule__TFunction__TypeVarsAssignment_3_1");
					put(grammarAccess.getTFunctionAccess().getTypeVarsAssignment_3_2_1(), "rule__TFunction__TypeVarsAssignment_3_2_1");
					put(grammarAccess.getTFunctionAccess().getNameAssignment_4(), "rule__TFunction__NameAssignment_4");
					put(grammarAccess.getTFunctionAccess().getFparsAssignment_6_0(), "rule__TFunction__FparsAssignment_6_0");
					put(grammarAccess.getTFunctionAccess().getFparsAssignment_6_1_1(), "rule__TFunction__FparsAssignment_6_1_1");
					put(grammarAccess.getTFunctionAccess().getReturnTypeRefAssignment_9(), "rule__TFunction__ReturnTypeRefAssignment_9");
					put(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TEnum__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TEnum__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTEnumAccess().getNameAssignment_3(), "rule__TEnum__NameAssignment_3");
					put(grammarAccess.getTEnumAccess().getLiteralsAssignment_5(), "rule__TEnum__LiteralsAssignment_5");
					put(grammarAccess.getTEnumAccess().getLiteralsAssignment_6_1(), "rule__TEnum__LiteralsAssignment_6_1");
					put(grammarAccess.getTEnumLiteralAccess().getNameAssignment(), "rule__TEnumLiteral__NameAssignment");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicAssignment_0_1(), "rule__TypeRefWithoutModifiers__DynamicAssignment_0_1");
					put(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0(), "rule__ThisTypeRefStructural__DefinedTypingStrategyAssignment_0");
					put(grammarAccess.getFunctionTypeExpressionAccess().getDeclaredThisTypeAssignment_2_3(), "rule__FunctionTypeExpression__DeclaredThisTypeAssignment_2_3");
					put(grammarAccess.getFunctionTypeExpressionAccess().getOwnedTypeVarsAssignment_4_1(), "rule__FunctionTypeExpression__OwnedTypeVarsAssignment_4_1");
					put(grammarAccess.getFunctionTypeExpressionAccess().getOwnedTypeVarsAssignment_4_2_1(), "rule__FunctionTypeExpression__OwnedTypeVarsAssignment_4_2_1");
					put(grammarAccess.getFunctionTypeExpressionAccess().getReturnTypeRefAssignment_8_1(), "rule__FunctionTypeExpression__ReturnTypeRefAssignment_8_1");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_0(), "rule__TAnonymousFormalParameterList__FparsAssignment_0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_1_1(), "rule__TAnonymousFormalParameterList__FparsAssignment_1_1");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicAssignment_0(), "rule__TAnonymousFormalParameter__VariadicAssignment_0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getNameAssignment_1_0(), "rule__TAnonymousFormalParameter__NameAssignment_1_0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefAssignment_2(), "rule__TAnonymousFormalParameter__TypeRefAssignment_2");
					put(grammarAccess.getTFormalParameterAccess().getVariadicAssignment_0(), "rule__TFormalParameter__VariadicAssignment_0");
					put(grammarAccess.getTFormalParameterAccess().getNameAssignment_1(), "rule__TFormalParameter__NameAssignment_1");
					put(grammarAccess.getTFormalParameterAccess().getTypeRefAssignment_3(), "rule__TFormalParameter__TypeRefAssignment_3");
					put(grammarAccess.getUnionTypeExpressionAccess().getTypeRefsAssignment_3(), "rule__UnionTypeExpression__TypeRefsAssignment_3");
					put(grammarAccess.getUnionTypeExpressionAccess().getTypeRefsAssignment_4_1(), "rule__UnionTypeExpression__TypeRefsAssignment_4_1");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getTypeRefsAssignment_3(), "rule__IntersectionTypeExpression__TypeRefsAssignment_3");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getTypeRefsAssignment_4_1(), "rule__IntersectionTypeExpression__TypeRefsAssignment_4_1");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0(), "rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getDeclaredTypeAssignment_1(), "rule__ParameterizedTypeRefStructural__DeclaredTypeAssignment_1");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgsAssignment_2_1(), "rule__ParameterizedTypeRefStructural__TypeArgsAssignment_2_1");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgsAssignment_2_2_1(), "rule__ParameterizedTypeRefStructural__TypeArgsAssignment_2_2_1");
					put(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersAssignment_1_0(), "rule__TStructMemberList__AstStructuralMembersAssignment_1_0");
					put(grammarAccess.getTStructMethodAccess().getTypeVarsAssignment_0_0_1_1(), "rule__TStructMethod__TypeVarsAssignment_0_0_1_1");
					put(grammarAccess.getTStructMethodAccess().getTypeVarsAssignment_0_0_1_2_1(), "rule__TStructMethod__TypeVarsAssignment_0_0_1_2_1");
					put(grammarAccess.getTStructMethodAccess().getNameAssignment_0_0_2(), "rule__TStructMethod__NameAssignment_0_0_2");
					put(grammarAccess.getTStructMethodAccess().getReturnTypeRefAssignment_3_1(), "rule__TStructMethod__ReturnTypeRefAssignment_3_1");
					put(grammarAccess.getTStructFieldAccess().getNameAssignment_0(), "rule__TStructField__NameAssignment_0");
					put(grammarAccess.getTStructFieldAccess().getTypeRefAssignment_1_1(), "rule__TStructField__TypeRefAssignment_1_1");
					put(grammarAccess.getTStructGetterAccess().getNameAssignment_0_0_2(), "rule__TStructGetter__NameAssignment_0_0_2");
					put(grammarAccess.getTStructGetterAccess().getDeclaredTypeRefAssignment_3_1(), "rule__TStructGetter__DeclaredTypeRefAssignment_3_1");
					put(grammarAccess.getTStructSetterAccess().getNameAssignment_0_0_2(), "rule__TStructSetter__NameAssignment_0_0_2");
					put(grammarAccess.getTStructSetterAccess().getFparAssignment_2(), "rule__TStructSetter__FparAssignment_2");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getDeclaredTypeAssignment_0(), "rule__ParameterizedTypeRefNominal__DeclaredTypeAssignment_0");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgsAssignment_1_1(), "rule__ParameterizedTypeRefNominal__TypeArgsAssignment_1_1");
					put(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgsAssignment_1_2_1(), "rule__ParameterizedTypeRefNominal__TypeArgsAssignment_1_2_1");
					put(grammarAccess.getConstructorTypeRefAccess().getStaticTypeRefAssignment_3(), "rule__ConstructorTypeRef__StaticTypeRefAssignment_3");
					put(grammarAccess.getClassifierTypeRefAccess().getStaticTypeRefAssignment_3(), "rule__ClassifierTypeRef__StaticTypeRefAssignment_3");
					put(grammarAccess.getWildcardAccess().getDeclaredUpperBoundAssignment_1_0_1(), "rule__Wildcard__DeclaredUpperBoundAssignment_1_0_1");
					put(grammarAccess.getWildcardAccess().getDeclaredLowerBoundAssignment_1_1_1(), "rule__Wildcard__DeclaredLowerBoundAssignment_1_1_1");
					put(grammarAccess.getTypeVariableAccess().getNameAssignment_0(), "rule__TypeVariable__NameAssignment_0");
					put(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundsAssignment_1_1(), "rule__TypeVariable__DeclaredUpperBoundsAssignment_1_1");
					put(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundsAssignment_1_2_1(), "rule__TypeVariable__DeclaredUpperBoundsAssignment_1_2_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			eu.numberfour.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser typedParser = (eu.numberfour.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser) parser;
			typedParser.entryRuleTypeDefs();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL" };
	}
	
	public TypesGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(TypesGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
