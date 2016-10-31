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

import com.google.inject.Binder
import eu.numberfour.n4js.languageGeneration.N4JSAntlrContentAssistGrammarGenerator
import org.eclipse.xtext.Grammar
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrContentAssistGrammarGenerator
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenerator

/**
 * Custom bindings for the language generation run.
 */
class N4MFGeneratorModule extends DefaultGeneratorModule {
	
	def configureXtextGeneratorNaming(Binder binder) {
		binder.bind(XtextGeneratorNaming).to(N4MFGeneratorNaming)
	}
	
	def configureAntlrGrammarGenerator(Binder binder) {
		binder.bind(AntlrGrammarGenerator).to(N4MFAntlrGrammarGenerator)
	}
	
	def configureAntlrContentAssistGrammarGenerator(Binder binder) {
		binder.bind(AntlrContentAssistGrammarGenerator).to(N4JSAntlrContentAssistGrammarGenerator)
	}
}

class N4MFGeneratorNaming extends XtextGeneratorNaming {
	
	override getGenericIdeBasePackage(Grammar grammar) {
		super.getEclipsePluginBasePackage(grammar)
	}
}
