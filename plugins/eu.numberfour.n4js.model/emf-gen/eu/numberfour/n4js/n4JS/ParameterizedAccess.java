/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS;

import eu.numberfour.n4js.ts.typeRefs.TypeRef;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.ParameterizedAccess#getTypeArgs <em>Type Args</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedAccess()
 * @model abstract="true"
 * @generated
 */
public interface ParameterizedAccess extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Args</b></em>' containment reference list.
	 * The list contents are of type {@link eu.numberfour.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Args</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Args</em>' containment reference list.
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getParameterizedAccess_TypeArgs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeRef> getTypeArgs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns <code>true<code> if the expression has type arguments.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.ts.typeRefs.TypeRef%>> _typeArgs = this.getTypeArgs();\nboolean _isEmpty = _typeArgs.isEmpty();\nreturn (!_isEmpty);'"
	 * @generated
	 */
	boolean isParameterized();

} // ParameterizedAccess