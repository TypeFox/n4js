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
package eu.numberfour.n4js.antlr.syntaxcoloring;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.xpand2.XpandExecutionContext;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.generator.Naming;
import org.eclipse.xtext.generator.NewlineNormalizer;
import org.eclipse.xtext.generator.parser.antlr.AntlrGrammarComparator;
import org.eclipse.xtext.generator.parser.antlr.ex.common.AbstractAntlrGeneratorFragmentEx;
import org.eclipse.xtext.generator.parser.antlr.ex.common.AntlrFragmentHelper;
import org.eclipse.xtext.generator.parser.antlr.ex.common.KeywordHelper;
import org.eclipse.xtext.generator.parser.antlr.postProcessing.SuppressWarningsProcessor;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Converts the Xtext grammar to an Antlr grammar that does allow to transform consumed token types to other token
 * types.
 */
public class HighlightingParserGeneratorFragment extends AbstractAntlrGeneratorFragmentEx {

	private final static Logger LOG = Logger.getLogger(HighlightingParserGeneratorFragment.class);

	@Override
	public void registerNaming(Naming naming) {
		super.registerNaming(naming);
		super.setFragmentHelper(new HighlightingFragmentHelper(naming));
	}

	@Override
	public void setFragmentHelper(AntlrFragmentHelper fragmentHelper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public HighlightingFragmentHelper getFragmentHelper() {
		return (HighlightingFragmentHelper) super.getFragmentHelper();
	}

	@Override
	public void generate(final Grammar grammar, XpandExecutionContext ctx) {
		final String srcGenPath = ctx.getOutput().getOutlet(Generator.SRC_GEN_UI).getPath();
		final String encoding = getEncoding(ctx, Generator.SRC_GEN_UI);
		final String lexerBaseFileName = ctx.getOutput().getOutlet(Generator.SRC_GEN).getPath() + "/"
				+ getFragmentHelper().getLexerGrammarFileName(grammar).replace('.', '/');
		String libPath = lexerBaseFileName.substring(0, lexerBaseFileName.lastIndexOf('/'));
		String absoluteLexerFileName = lexerBaseFileName + ".g";

		String absoluteParserFileName = srcGenPath + "/"
				+ getFragmentHelper().getHighlightingParserGrammarFileName(grammar).replace('.', '/') + ".g";

		final File parserFile = new File(absoluteParserFileName);

		if (parserFile.exists()) {
			// there is already a grammar file if this fragment is executed after HighlightingParserGeneratorFragment2
			// as part of the new workflow for the sake of validating the new grammar generator

			// rename the existing file...
			final File renamedParserFile = new File(absoluteParserFileName.replaceFirst("\\.g$", "New.g"));

			try {
				Files.move(parserFile, renamedParserFile);

			} catch (IOException e) {
				System.err.println("Couldn't rename the existing grammar file " + absoluteParserFileName);
				e.printStackTrace();
			}

			// ... generate the grammar file the old way ...
			KeywordHelper helper = new KeywordHelper(grammar, getOptions().isIgnoreCase());
			super.generate(grammar, ctx);
			helper.discardHelper(grammar);

			final File referenceParserFile = new File(absoluteParserFileName.replaceFirst("\\.g$", "Reference.g"));
			try {
				// remove the above generated grammar file to «name»Reference.g
				Files.move(parserFile, referenceParserFile);
				// and revert the renaming of the original grammar that goes into the version control
				Files.move(renamedParserFile, parserFile);

			} catch (IOException e) {
				System.err.println("Couldn't rename the existing grammar file " + absoluteParserFileName);
				e.printStackTrace();
			}

			// ... and check the grammar files for any differences
			// note that the internal parser class has been generated in HighlightingParserGeneratorFragment2
			compareHighlightingGrammars(referenceParserFile, parserFile);

			// finally delete the above generate reference grammar.
			referenceParserFile.delete();

			return;
		}

		KeywordHelper helper = new KeywordHelper(grammar, getOptions().isIgnoreCase());
		super.generate(grammar, ctx);

		addAntlrParam("-fo");
		addAntlrParam(absoluteParserFileName.substring(0, absoluteParserFileName.lastIndexOf('/')));
		addAntlrParam("-lib");
		addAntlrParam(libPath);
		getAntlrTool().runWithEncodingAndParams(absoluteParserFileName, encoding, getAntlrParams());
		Charset charset = Charset.forName(encoding);
		simplifyUnorderedGroupPredicatesIfRequired(grammar, absoluteParserFileName, charset);
		splitParserAndLexerIfEnabled(absoluteLexerFileName, absoluteParserFileName, charset);
		String generatedParserClass = absoluteParserFileName.replaceAll("\\.g$", getParserFileNameSuffix());
		suppressWarningsImpl(generatedParserClass, charset);
		normalizeLineDelimitersImpl(generatedParserClass, charset);
		normalizeTokens(absoluteParserFileName, charset);
		helper.discardHelper(grammar);
	}

	@Override
	protected void splitLexerClassFile(String filename, Charset encoding) throws IOException {
		// nothing to do
	}

	private void suppressWarningsImpl(String javaFile, Charset encoding) {
		String content = readFileIntoString(javaFile, encoding);
		content = new SuppressWarningsProcessor().process(content);
		writeStringIntoFile(javaFile, content, encoding);
	}

	private void normalizeLineDelimitersImpl(String textFile, Charset encoding) {
		String content = readFileIntoString(textFile, encoding);
		content = new NewlineNormalizer(getLineDelimiter()) {
			// Antlr tries to outsmart us by using a line length that depends on the system
			// line delimiter when it splits a very long String (encoded DFA) into a
			// string concatenation
			// Here we join these lines again.
			@Override
			public String normalizeLineDelimiters(CharSequence cs) {
				String result = super.normalizeLineDelimiters(cs);
				result = result.replaceAll("\"\\+\\n\\s+\"", "");
				return result;
			}
		}.normalizeLineDelimiters(content);
		writeStringIntoFile(textFile, content, encoding);
	}

	@Override
	public void checkConfiguration(Issues issues) {
		super.checkConfiguration(issues);
		if (getOptions().isBacktrackLexer() && getOptions().isIgnoreCase())
			issues.addError("Backtracking lexer and ignorecase cannot be combined for now.");
	}

	private void compareHighlightingGrammars(File xpandBasedGenerated, File xtendBasedGenerated) {
		final AntlrGrammarComparator comparator = new AntlrGrammarComparator();

		try {
			final String grammarContentRef = Files.toString(xpandBasedGenerated, Charsets.UTF_8);
			final String grammarContent = Files.toString(xtendBasedGenerated, Charsets.UTF_8);

			LOG.info("Comparison of highlighting parser grammar started...");
			comparator.compareGrammars(grammarContent, grammarContentRef, new AntlrGrammarComparator.IErrorHandler() {

				@Override
				public void handleInvalidGeneratedGrammarFile(AntlrGrammarComparator.ErrorContext context) {
					throw new RuntimeException("invalid grammar file");
				}

				@Override
				public void handleInvalidReferenceGrammarFile(AntlrGrammarComparator.ErrorContext context) {
					throw new RuntimeException("invalid reference grammar file");
				}

				@Override
				public void handleMismatch(String matched, String expected,
						AntlrGrammarComparator.ErrorContext context) {
					throw new RuntimeException("mismatch at " + matched + ", expected " + expected +
							" in line " + context.getTestedGrammar().getLineNumber() +
							" (line " + context.getReferenceGrammar().getLineNumber() + ")");
				}
			});

			LOG.info("...succeeded!");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
