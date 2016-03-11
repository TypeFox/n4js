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
package eu.numberfour.n4js.ts.conversions;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import eu.numberfour.n4js.ts.types.UndefModifier;

/**
 * Converts explicit declared undef modifiers to {@link UndefModifier} and vice versa. Note that the converter is not
 * symmetrical. That is, {@code toValue(toString(UndefModifier.ISUNDEF),null)} is converted to {@link UndefModifier#NA}.
 */
public class UndefModifierValueConverter implements IValueConverter<UndefModifier> {

	static final String MANDATORY_TOKEN = "!";
	static final String OPTIONAL_TOKEN = "?";

	/* (non-Javadoc)
	 * @see org.eclipse.xtext.conversion.IValueConverter#toValue(java.lang.String, org.eclipse.xtext.nodemodel.INode)
	 */
	@Override
	public UndefModifier toValue(String string, INode node)
			throws ValueConverterException {
		if (OPTIONAL_TOKEN.equals(string)) {
			return UndefModifier.OPTIONAL;
		}
		if (MANDATORY_TOKEN.equals(string)) {
			return UndefModifier.MANDATORY;
		}
		return UndefModifier.NA;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.xtext.conversion.IValueConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(UndefModifier value) throws ValueConverterException {
		switch (value) {
		case OPTIONAL: return OPTIONAL_TOKEN;
		case MANDATORY: return MANDATORY_TOKEN;
		default: return "";
		}
	}

}
