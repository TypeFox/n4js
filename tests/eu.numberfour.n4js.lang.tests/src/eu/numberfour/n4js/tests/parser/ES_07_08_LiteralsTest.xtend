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

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.n4JS.BooleanLiteral
import eu.numberfour.n4js.n4JS.MultiplicativeExpression
import eu.numberfour.n4js.n4JS.NullLiteral
import eu.numberfour.n4js.n4JS.Script
import eu.numberfour.n4js.n4JS.RegularExpressionLiteral
import eu.numberfour.n4js.n4JS.StringLiteral
import eu.numberfour.n4js.n4JS.VariableDeclaration
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import eu.numberfour.n4js.n4JS.Expression
import eu.numberfour.n4js.n4JS.NumericLiteral

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSInjectorProvider))
class ES_07_08_LiteralsTest {

	@Inject
	extension ParseHelper<Script>

	@Test
	def void testLiterals() {
		val program = '''
			var string = "Hello";
			var num = 1;
			var bool = true;
			var nullL = null;
			var regexp = /someregex/;
			var division = 12 / 5;
		'''.parse

		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)

		val varDecls = program.eAllContents.filter(VariableDeclaration)
		assertType(StringLiteral, varDecls.findFirst[name=="string"].expression)
		assertType(NumericLiteral, varDecls.findFirst[name=="num"].expression)
		assertType(BooleanLiteral, varDecls.findFirst[name=="bool"].expression)
		assertType(NullLiteral, varDecls.findFirst[name=="nullL"].expression)
		assertType(RegularExpressionLiteral, varDecls.findFirst[name=="regexp"].expression)
		assertType(MultiplicativeExpression, varDecls.findFirst[name=="division"].expression)
	}

	def assertType(Class<?> type, Expression expression) {
		assertNotNull("Expected type " + type.name + ", was null", expression)
		assertTrue("Expected type " + type.name + ", was " + expression.eClass.name,
			type.isInstance(expression)
		)
	}

}
