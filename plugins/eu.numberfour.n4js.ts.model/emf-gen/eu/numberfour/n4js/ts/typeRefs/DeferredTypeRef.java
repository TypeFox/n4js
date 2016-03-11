/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.ts.typeRefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deferred Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A TypeRef used by the types builder to denote that some other part of the implementation is responsible
 * for creating a certain type reference. FIXME more info
 * <!-- end-model-doc -->
 *
 *
 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getDeferredTypeRef()
 * @model
 * @generated
 */
public interface DeferredTypeRef extends TypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return \"*deferred*\";'"
	 * @generated
	 */
	String getTypeRefAsString();

} // DeferredTypeRef
