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
package eu.numberfour.n4js.antlr;

import org.eclipse.xpand2.XpandExecutionContext;
import org.eclipse.xtend.expression.ExecutionContextImpl;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.parser.antlr.ex.ca.ContentAssistParserGeneratorFragment;

/**
 * We have to improve keyword handling, such that keywords are allowed with unicode chars.
 */
public class ContentAssistParserGeneratorFragmentWithUniCodeChars extends ContentAssistParserGeneratorFragment {

	@Override
	public void generate(Grammar grammar, XpandExecutionContext ctx) {

		// similar to customization of AntlrGeneratorWithCustomKeywordLogic

		// Xtend AOP feature: replace specified extensions with custom
		// implementation for unicode keyword lexer rules
		ExecutionContextImpl casted = (ExecutionContextImpl) ctx;
		casted.registerExtensionAdvices("eu::numberfour::n4js::antlr::UnicodeAwareKeywords");
		super.generate(grammar, ctx);
	}

	@Override
	protected String getTemplate() {
		return org.eclipse.xtext.generator.parser.antlr.ex.ca.ContentAssistParserGeneratorFragment.class.getName()
				.replaceAll("\\.", "::");
	}
}
