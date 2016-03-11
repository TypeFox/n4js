/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS;

import eu.numberfour.n4js.ts.types.TMember;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Setter Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getN4SetterDeclaration()
 * @model
 * @generated
 */
public interface N4SetterDeclaration extends SetterDeclaration, N4FieldAccessor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getDefinedSetter();'"
	 * @generated
	 */
	TMember getDefinedTypeElement();

} // N4SetterDeclaration
