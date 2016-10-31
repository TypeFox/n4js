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
package eu.numberfour.n4js.languageGeneration.n4mf

import eu.numberfour.n4js.antlr.UnicodeKeywordHelper
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenerator
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions

/**
 * Customized production grammar generation.
 */
class N4MFAntlrGrammarGenerator extends AntlrGrammarGenerator {
	
	/**
	 * Replace specified extensions with custom implementation for unicode keyword lexer rules
	 */
	override protected toAntlrKeywordRule(String keyword, AntlrOptions options) {
		UnicodeKeywordHelper.toUnicodeKeyword(keyword)
	}
}
