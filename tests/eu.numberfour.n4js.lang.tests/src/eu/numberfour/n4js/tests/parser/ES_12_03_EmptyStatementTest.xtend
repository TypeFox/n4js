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
package eu.numberfour.n4js.tests.parser

import eu.numberfour.n4js.N4JSInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import eu.numberfour.n4js.n4JS.EmptyStatement

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSInjectorProvider))
class ES_12_03_EmptyStatementTest extends AbstractParserTest {

	@Test
	def void testEmptyFile() {
		val script = ''.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testOnlyLineBreak() {
		val script = '\n'.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testOnlyComment() {
		val script = '/*\n*/'.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testOnlyLineComment() {
		val script = '// comment '.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testOnlyEmptyLineComment() {
		val script = '//'.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testOnlyLineCommentWithLineBreak() {
		val script = '// comment\n'.parseSuccessfully
		assertTrue(script.scriptElements.empty)
	}

	@Test
	def void testEmptyStatement() {
		val script = ';'.parseSuccessfully
		assertTrue(script.scriptElements.head instanceof EmptyStatement)
	}

	@Test
	def void testEmptyStatementAfterLineBreak() {
		val script = '\r\n;'.parseSuccessfully
		assertTrue(script.scriptElements.head instanceof EmptyStatement)
	}

}
