/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package eu.numberfour.n4js.xpect.scoping;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.xpect.expectation.CommaSeparatedValuesExpectation;
import org.xpect.expectation.ICommaSeparatedValuesExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;
import org.xpect.xtext.lib.tests.ScopingTest;
import org.xpect.xtext.lib.util.XtextOffsetAdapter.ICrossEReferenceAndEObject;

import com.google.inject.Inject;

import eu.numberfour.n4js.n4JS.ParameterizedPropertyAccessExpression;
import eu.numberfour.n4js.ts.types.IdentifiableElement;
import eu.numberfour.n4js.ts.types.TMember;

/**
 * @author Joerg Reichert <joerg.reichert@itemis.de> - Initial contribution and API
 * @author Jens von Pilgrim <jens.von.pilgrim@numberfour.eu> - Refactored to use line numbers etc.
 *
 */
public class ScopeXpectMethod extends ScopingTest {

	@Inject
	private IQualifiedNameConverter converter;

	@Inject
	private IScopeProvider scopeProvider;

	/**
	 * Compares scope including resource name and line number.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void scopeWithPosition( //
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation, //
			ICrossEReferenceAndEObject arg1 //
	) {
		EObject eobj = arg1.getEObject();
		IScope scope = scopeProvider.getScope(eobj, arg1.getCrossEReference());
		for (IEObjectDescription eo : scope.getAllElements()) {
			eo.getEObjectURI();
		}
		URI uri = eobj == null ? null : eobj.eResource() == null ? null : eobj.eResource().getURI();
		expectation.assertEquals(new ScopeAwareIterable(uri, true, scope),
				new IsInScopeWithOptionalPositionPredicate(converter,
						uri, true, scope));
	}

	/**
	 * Compares scope including resource name but not line number.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void scopeWithResource( //
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation, //
			ICrossEReferenceAndEObject arg1 //
	) {
		EObject eobj = arg1.getEObject();
		IScope scope = scopeProvider.getScope(eobj, arg1.getCrossEReference());
		for (IEObjectDescription eo : scope.getAllElements()) {
			eo.getEObjectURI();
		}
		URI uri = eobj == null ? null : eobj.eResource() == null ? null : eobj.eResource().getURI();
		expectation.assertEquals(new ScopeAwareIterable(uri, false, scope),
				new IsInScopeWithOptionalPositionPredicate(converter,
						uri, false, scope));
	}

	@Override
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void scope( //
			@CommaSeparatedValuesExpectation ICommaSeparatedValuesExpectation expectation, //
			ICrossEReferenceAndEObject arg1 //
	) {
		EObject eobj = arg1.getEObject();
		IScope scope = scopeProvider.getScope(eobj, arg1.getCrossEReference());
		URI uri = eobj == null ? null : eobj.eResource() == null ? null : eobj.eResource().getURI();
		expectation.assertEquals(new ScopeAllElements(scope), new IsInScopeWithOptionalPositionPredicate(converter,
				uri, false,
				scope));
	}

	/**
	 * Checks that a given element is bound to something identified by (simple) qualified name. The check is designed as
	 * simple as possible. That is, simply the next following expression is tested, and within that we expect a property
	 * access or a direct identifiable element. The compared name is the simple qualified name, that is container (type)
	 * followed by elements name, without URIs of modules etc.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void binding( //
			@CommaSeparatedValuesExpectation ICommaSeparatedValuesExpectation expectation, //
			ICrossEReferenceAndEObject arg1 //
	) {
		EObject eobj = arg1.getEObject();
		ParameterizedPropertyAccessExpression ppae = EcoreUtil2.getContainerOfType(eobj,
				ParameterizedPropertyAccessExpression.class);
		IdentifiableElement element;

		if (ppae != null) {
			element = ppae.getProperty();
		} else if (eobj instanceof IdentifiableElement) {
			element = (IdentifiableElement) eobj;
		} else {
			throw new IllegalArgumentException("Cannot check binding for "
					+ (eobj == null ? "null" : eobj.eClass().getName()));
		}
		String container = "";
		if (element instanceof TMember) {
			container = ((TMember) element).getContainingType().getName() + ".";
		}
		final String qn = container + element.getName();
		// URI uri = eobj == null ? null : eobj.eResource() == null ? null : eobj.eResource().getURI();
		expectation.assertEquals(
				Collections.singleton(qn));
	}
}
