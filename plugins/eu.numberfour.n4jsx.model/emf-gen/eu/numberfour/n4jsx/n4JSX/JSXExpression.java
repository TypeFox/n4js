/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4jsx.n4JSX;

import eu.numberfour.n4js.n4JS.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JSX Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4jsx.n4JSX.JSXExpression#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4jsx.n4JSX.N4JSXPackage#getJSXExpression()
 * @model
 * @generated
 */
public interface JSXExpression extends JSXChild {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see eu.numberfour.n4jsx.n4JSX.N4JSXPackage#getJSXExpression_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4jsx.n4JSX.JSXExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

} // JSXExpression
