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

import eu.numberfour.n4js.N4JSInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static eu.numberfour.n4js.validation.JavaScriptVariant.*

/**
 * Test class for operator test (6.1.10- 6.1.18)
 *
 * @see https://numberfour.jira.com/browse/IDE-345
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_12_MultiplicativeExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {


	@Test
	def void testType() {
		for (mode : values()) {
			assertOperatorType(mode, "number", '''3*4''')
			assertOperatorType(mode, "number", '''"hello"*"world"''')
			assertOperatorType(mode, "number", '''n1*n2''')
			assertOperatorType(mode, "number", '''3/4''')
			assertOperatorType(mode, "number", '''"hello"/"world"''')
			assertOperatorType(mode, "number", '''n1/n2''')
			assertOperatorType(mode, "number", '''3%4''')
			assertOperatorType(mode, "number", '''"hello"%"world"''')
			assertOperatorType(mode, "number", '''n1%n2''')
		}
	}

	@Test
	def void testExpectedType() {
		assertBinaryOperatorExpectedType(unrestricted, "any", "any", "n1*n2");
		assertBinaryOperatorExpectedType(strict, "any", "any", "n1*n2");
		assertBinaryOperatorExpectedType(n4js, "number", "number", "n1*n2");
	}

}
