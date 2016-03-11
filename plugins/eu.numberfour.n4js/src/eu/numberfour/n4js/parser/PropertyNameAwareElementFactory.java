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
package eu.numberfour.n4js.parser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.ValueConverterWithValueException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.DefaultEcoreElementFactory;
import org.eclipse.xtext.xtext.RuleNames;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.numberfour.n4js.conversion.N4JSValueConverterException;
import eu.numberfour.n4js.n4JS.N4JSPackage;
import eu.numberfour.n4js.n4JS.PropertyAssignment;
import eu.numberfour.n4js.n4JS.PropertyNameKind;
import eu.numberfour.n4js.n4JS.PropertyNameOwner;
import eu.numberfour.n4js.n4JS.StringLiteral;
import eu.numberfour.n4js.services.N4JSGrammarAccess;
import eu.numberfour.n4js.validation.IssueCodes;

/**
 * <p>
 * This keeps track on the concrete syntax that was used to parse a property assign.
 * </p>
 */
@SuppressWarnings("restriction")
@Singleton
public class PropertyNameAwareElementFactory extends DefaultEcoreElementFactory {

	private String stringRuleName;
	private String identifierRuleName;
	private String numberRuleName;
	private String computedFromExpressionRuleName;
	private String computedFromStringRuleName;

	@Inject
	private void readRuleNames(N4JSGrammarAccess grammarAccess, RuleNames ruleNames) {
		stringRuleName = ruleNames.getQualifiedName(grammarAccess.getSTRINGRule());
		identifierRuleName = ruleNames.getQualifiedName(grammarAccess.getIdentifierNameRule());
		numberRuleName = ruleNames.getQualifiedName(grammarAccess.getNumericLiteralAsStringRule());
		computedFromExpressionRuleName = ruleNames.getQualifiedName(grammarAccess.getSymbolLiteralComputedNameRule());
		computedFromStringRuleName = ruleNames.getQualifiedName(grammarAccess.getStringLiteralAsNameRule());
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * If we parsed a {@link PropertyAssignment}, the set operation also initializes the {@link PropertyNameKind}.
	 * </p>
	 */
	@Override
	public void set(EObject object, String feature, Object value, String ruleName, INode node)
			throws N4JSValueConverterException {
		final EStructuralFeature structuralFeature = object.eClass().getEStructuralFeature(feature);
		if (structuralFeature == null)
			throw new IllegalArgumentException(object.eClass().getName() + "." + feature + " does not exist");

		try {
			final Object tokenValue = getTokenValue(value, ruleName, node);
			checkNullForPrimitiveFeatures(structuralFeature, tokenValue, node);
			object.eSet(structuralFeature, tokenValue);

			// this call is an extra to the super class method
			setPropertyNameKind(object, feature, ruleName);

			setRawStringValue(object, feature, value);

		} catch (ValueConverterWithValueException e) {
			final Object tokenValue = e.getValue();
			checkNullForPrimitiveFeatures(structuralFeature, tokenValue, node);
			object.eSet(structuralFeature, tokenValue);

			// this call is an extra to the super class method
			setPropertyNameKind(object, feature, ruleName);

			setRawStringValue(object, feature, value);

			throw e;
		} catch (ValueConverterException e) {
			throw e;
		} catch (NullPointerException e) {
			throw new N4JSValueConverterException(IssueCodes.getMessageForVCO_NPE(), IssueCodes.VCO_NPE, node, e);
		} catch (Exception e) {
			throw new ValueConverterException(null, node, e);
		}
	}

	private void setRawStringValue(EObject object, String feature, Object value) {
		if (object.eClass() == N4JSPackage.Literals.STRING_LITERAL
				&& N4JSPackage.Literals.STRING_LITERAL__VALUE.getName().equals(feature)) {
			StringLiteral casted = (StringLiteral) object;
			casted.setRawValue((String) getTokenAsStringIfPossible(value));
		}
	}

	private void setPropertyNameKind(EObject object, String feature, String ruleName) {
		if ("name".equals(feature) && object instanceof PropertyNameOwner) {
			final PropertyNameOwner nameOwner = (PropertyNameOwner) object;
			if (identifierRuleName.equals(ruleName)) {
				nameOwner.setKind(PropertyNameKind.IDENTIFIER);
			} else if (stringRuleName.equals(ruleName)) {
				nameOwner.setKind(PropertyNameKind.STRING);
			} else if (numberRuleName.equals(ruleName)) {
				nameOwner.setKind(PropertyNameKind.NUMBER);
			} else if (computedFromStringRuleName.equals(ruleName)) {
				nameOwner.setKind(PropertyNameKind.COMPUTED_FROM_STRING_LITERAL);
			} else if (computedFromExpressionRuleName.equals(ruleName)) {
				nameOwner.setKind(PropertyNameKind.COMPUTED_FROM_IDENTIFIER_EXPR);
			} else {
				throw new IllegalArgumentException(ruleName);
			}
		}
	}

	// only a copy of super class method
	private Object getTokenValue(Object tokenOrValue, String ruleName, INode node) throws N4JSValueConverterException {
		Object value = getTokenAsStringIfPossible(tokenOrValue);
		if ((value == null || value instanceof CharSequence) && ruleName != null) {
			value = getConverterService().toValue(value == null ? null : value.toString(), ruleName, node);
		}
		return value;
	}

	// only a copy of super class method
	private void checkNullForPrimitiveFeatures(EStructuralFeature structuralFeature, Object tokenValue, INode node) {
		if (tokenValue == null && structuralFeature.getEType().getInstanceClass().isPrimitive()) {
			throw new N4JSValueConverterException(
					IssueCodes.getMessageForVCO_NULL_FEATURE(structuralFeature.getName()), IssueCodes.VCO_NULL_FEATURE,
					node, null);
		}
	}
}
