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
package eu.numberfour.n4js.tests.scoping

import com.google.inject.Inject
import eu.numberfour.n4js.N4JSInjectorProvider
import eu.numberfour.n4js.n4JS.ExportDeclaration
import eu.numberfour.n4js.n4JS.N4ClassDeclaration
import eu.numberfour.n4js.n4JS.N4MethodDeclaration
import eu.numberfour.n4js.n4JS.Script
import eu.numberfour.n4js.ts.typeRefs.ParameterizedTypeRef
import eu.numberfour.n4js.ts.types.TClass
import eu.numberfour.n4js.ts.types.TField
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import eu.numberfour.n4js.ts.types.TMethod
import com.google.inject.Provider

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class TypeScopingTest {

	@Inject extension ParseHelper<Script>

	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Test
	def void testDuplicateImport() {
		val rs = resourceSetProvider.get
		val uri1 = URI.createURI('c.n4js')
		val script1 = '''
			@Internal public class C {
			}
		'''.parse(uri1, rs)
		val uri2 = URI.createURI('d.n4js')
		val script2 = '''
			import { C as C } from 'c'
			import { C as B } from 'c'
			import { C as A } from 'c'

			@Internal public class D {
				m(b: B): A {
				}
			}
		'''.parse(uri2, rs)
		val c = script1.scriptElements.last as N4ClassDeclaration
		val d = script2.scriptElements.last as N4ClassDeclaration
		val m = d.ownedMembers.head as N4MethodDeclaration
		val returnType = (m.returnTypeRef as ParameterizedTypeRef).declaredType
		val firstParamType = (m.fpars.head.declaredTypeRef as ParameterizedTypeRef).declaredType
		Assert.assertSame(returnType, firstParamType)
		Assert.assertSame(c.definedTypeAsClass, returnType)
	}

	@Test
	def void testTypeVariable() {
		val rs = resourceSetProvider.get
		val uri = URI.createURI('c.n4js')
		val script = '''
			@Internal public class C<A> {
				<B> m(b: B): A {
				}
			}
		'''.parse(uri, rs)
		val c = script.scriptElements.last as N4ClassDeclaration
		val m = c.ownedMembers.head as N4MethodDeclaration
		val returnType = (m.returnTypeRef as ParameterizedTypeRef).declaredType
		val firstParamType = (m.fpars.head.declaredTypeRef as ParameterizedTypeRef).declaredType
		Assert.assertSame((m.definedType as TMethod).typeVars.head, firstParamType)
		Assert.assertSame(c.definedTypeAsClass.typeVars.head, returnType)
	}

	@Test
	def void testFieldType() {
		val script = '''
			export project enum StorageType {
				FILESYSTEM, DATABASE, CLOUD
			}
			export public interface Element {
			}
			export public class Storage<E extends Element> {
				private type: StorageType;
			}
		'''.parse
		val storage = (script.scriptElements.last as ExportDeclaration).exportedElement as N4ClassDeclaration
		val definedClass = storage.definedType as TClass
		val field = definedClass.ownedMembers.head as TField
		val fieldType = (field.typeRef as ParameterizedTypeRef).declaredType
		Assert.assertFalse(fieldType.eIsProxy)
	}
}
