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
package eu.numberfour.n4js.xsemantics

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.n4JS.ExpressionStatement
import eu.numberfour.n4js.n4JS.ParenExpression
import eu.numberfour.n4js.n4JS.Script
import eu.numberfour.n4js.typesystem.RuleEnvironmentExtensions
import eu.numberfour.n4js.ts.utils.TypeCompareHelper
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * N4JS Spec Test: 6.1.6. Parenthesized Expression and Grouping Operator, Type Inference
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_06_ParenthesizedExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Inject extension ValidationTestHelper

	@Inject
	extension TypeCompareHelper

	final static CharSequence scriptPrefix = '''
			class A{}
			class B extends A{}
			var f: boolean;
			var a: A;
			var b: B;
	'''

	def void assertParenthesisedExpressionType(String expression) {
		val script = (scriptPrefix + expression + ";\n" + "(" + expression + ")").parse();

		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val pexpr = (script.scriptElements.reverseView.head as ExpressionStatement).expression;
		val expr = (script.scriptElements.reverseView.tail.head as ExpressionStatement).expression;
		assertTrue(pexpr instanceof ParenExpression)
		val typeExpr = checkedType(G, expr)
		val typePExpr = checkedType(G, pexpr)
		assertEquals(typeExpr.typeRefAsString, typePExpr.typeRefAsString);
		assertTrue(typeExpr.typeRefAsString + " is correct, but something went wrong as type is not compared as equivalent", compare(typeExpr, typePExpr)==0);
	}

	@Test
	def void testParenthesisedExpressionType() {
		assertParenthesisedExpressionType('''10''');
		assertParenthesisedExpressionType('''"hello"''');
		assertParenthesisedExpressionType('''true''');
		assertParenthesisedExpressionType('''a''');
		assertParenthesisedExpressionType('''10-5''');
//		assertParenthesisedExpressionType('''f?a:b'''); see IDE-348 (6.1.19. Conditional Expression)
	}

	@Test
	def void testValidateSpecExample() {
		val script = '''
		class A{} class B extends A{}
		var f: boolean; var a: A; var b: B;

		/* simple		<->		parenthesized */
		10;						(10);
		"hello";				("hello");
		true;					(true);
		a;						(a);
		10-5;					(10-5);
«««		f?a:b					(f?a:b); see IDE-348 (6.1.19. Conditional Expression)
		'''.parse;
		assertNoErrors(script)
	}


}
