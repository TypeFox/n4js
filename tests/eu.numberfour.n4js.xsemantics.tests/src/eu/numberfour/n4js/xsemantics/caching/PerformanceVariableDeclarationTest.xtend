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
package eu.numberfour.n4js.xsemantics.caching

import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.xsemantics.caching.AbstractTypesystemForPerformanceTest
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test performance involving typing expressions
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class PerformanceVariableDeclarationTest extends AbstractTypesystemForPerformanceTest {

	@Test
	def void testVarDecl5() {
		5.varTyping
	}

	@Test
	def void testVarDecl10() {
		10.varTyping
	}

	@Test
	def void testVarDecl20() {
		20.varTyping
	}

	@Test
	def void testVarDecl100() {
		// DON'T try to do that without caching :)
		100.varTyping
	}

	def protected varTyping(int n) {
		/*
		 * var i_0 = 0;
		 * var i_1 = i_0;
		 * var i_2 = i_0 + i_1;
		 * var i_3 = i_0 + i_1 + i_2;
		 * etc..
		 */
		'''
			var i_0 = 0;
			var i_1 = i_0;
			«FOR i : 2..n»
			var i_«i» = i_0«FOR j : 1..i-1» + i_«j»«ENDFOR»;
			«ENDFOR»
		'''.assertValidate(0)
	}

}
