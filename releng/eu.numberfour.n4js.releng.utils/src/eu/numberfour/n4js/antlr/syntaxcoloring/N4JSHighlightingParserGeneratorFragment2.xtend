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
package eu.numberfour.n4js.antlr.syntaxcoloring

import com.google.inject.Inject
import eu.numberfour.n4js.antlr.syntaxcoloring.N4JSHighlightingParserGeneratorFragment2.GuardedXtextGeneratorFileSystemAccess
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessExtensions
import org.eclipse.xtext.xtext.generator.model.IXtextGeneratorFileSystemAccess
import org.eclipse.xtext.xtext.generator.model.TypeReference
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammar
import org.eclipse.xtext.xtext.generator.parser.antlr.GrammarNaming
import org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2

/**
 * Custom highlighting parser generator fragment.
 */
class N4JSHighlightingParserGeneratorFragment2 extends XtextAntlrGeneratorFragment2 {
	
	@Inject extension GrammarAccessExtensions grammarUtil
	@Inject GrammarNaming productionNaming
	@Inject extension N4JSHighlightingGrammarNaming highlightingNaming

	@Inject N4JSAntlrHighlightingGrammarGenerator highlightingGenerator

	@Accessors(PROTECTED_GETTER)	
	val boolean combinedGrammar = false
	
	override protected doGenerate() {
		// The following 2 lines are deactivated as they would attempt to install
		//  adapters on the grammar AST. This already done by the production parser
		//  generator fragment, but is requirement once that fragment is deactivated,
		//  e.g. for testing purposes.  
		// new KeywordHelper(grammar, options.ignoreCase, grammarUtil)
		// new CombinedGrammarMarker(false).attachToEmfObject(grammar)

		generateHighlightingGrammar()
	}
	
	@FinalFieldsConstructor
	static class GuardedXtextGeneratorFileSystemAccess implements IXtextGeneratorFileSystemAccess {
		
		@Delegate
		val IXtextGeneratorFileSystemAccess delegate
	}
		
	protected def generateHighlightingGrammar() {
		val fsa = projectConfig.eclipsePlugin.srcGen
		
		highlightingGenerator.generate(grammar, options, new GuardedXtextGeneratorFileSystemAccess(fsa) {
			
			/**
			 * Via this customization the generation of an additional lexer grammar is suppressed,
			 *  since the production lexer is re-used.
			 * Can be removed once https://github.com/eclipse/xtext-core/issues/158
	 		 *  is resolved and the solution is leveraged in N4JSAntlrHighlightingGrammarGenerator. 
			 */
			override generateFile(String fileName, CharSequence contents) {
				if (contents !== null)
					super.generateFile(fileName, contents)
			}
		})
		
		runAntlr(grammar.parserGrammar, null, fsa)
		
		simplifyUnorderedGroupPredicatesIfRequired(grammar, fsa, grammar.internalParserClass)
		splitParserAndLexerIfEnabled(fsa, grammar.internalParserClass, null)
		normalizeTokens(fsa, grammar.parserGrammar.tokensFileName)
		suppressWarnings(fsa, grammar.internalParserClass /* , grammar.lexerClass */)
		normalizeLineDelimiters(fsa, grammar.internalParserClass /*, grammar.lexerClass */)
	}
	
	protected override runAntlr(AntlrGrammar parserGrammar, AntlrGrammar lexerGrammar, IXtextGeneratorFileSystemAccess fsa) {
		val src_gen = projectConfig.runtime.srcGen
		val src_gen_ui = projectConfig.eclipsePlugin.srcGen
		
		val theLexerGrammar = productionNaming.getLexerGrammar(grammar)
		
		val encoding = codeConfig.encoding
		val lexerGrammarFile = '''«src_gen.path»/«theLexerGrammar.grammarFileName»'''
		val lexerOutputDir = lexerGrammarFile.substring(0, lexerGrammarFile.lastIndexOf('/'))
		
		val parserGrammarFile = '''«src_gen_ui.path»/«parserGrammar.grammarFileName»'''
		val parserAntlrParams = newArrayList(antlrParams)
		parserAntlrParams += "-fo" 
		parserAntlrParams += parserGrammarFile.substring(0, parserGrammarFile.lastIndexOf('/'))
		parserAntlrParams += "-lib"
		parserAntlrParams += lexerOutputDir

		antlrTool.runWithEncodingAndParams(parserGrammarFile, encoding, parserAntlrParams)
		normalizeTokens(fsa, parserGrammar.tokensFileName);
	}

	protected override void splitParserAndLexerIfEnabled(IXtextGeneratorFileSystemAccess fsa, TypeReference parser, TypeReference lexer) {
		improveCodeQuality(fsa, lexer, parser)
		if (getOptions().isClassSplitting()) {
			// the lexer is reused, don't touch it
			// splitLexerClassFile(fsa, lexer)
			splitParserClassFile(projectConfig.eclipsePlugin.srcGen, parser)
		}
	}
	
	protected override improveCodeQuality(IXtextGeneratorFileSystemAccess fsa, TypeReference lexer, TypeReference parser) {
		// the lexer is reused, don't touch it
		// var lexerContent = fsa.readTextFile(lexer.javaPath).toString
		// lexerContent = codeQualityHelper.stripUnnecessaryComments(lexerContent, options)
		// fsa.generateFile(lexer.javaPath, lexerContent)

		var parserContent = fsa.readTextFile(parser.javaPath).toString
		parserContent = codeQualityHelper.stripUnnecessaryComments(parserContent, options)
		parserContent = codeQualityHelper.removeDuplicateBitsets(parserContent, options)
		parserContent = codeQualityHelper.removeDuplicateDFAs(parserContent, options)
		fsa.generateFile(parser.javaPath, parserContent)
	}
}