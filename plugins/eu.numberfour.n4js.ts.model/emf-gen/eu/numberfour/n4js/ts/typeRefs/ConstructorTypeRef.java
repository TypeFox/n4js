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
 * A representation of the model object '<em><b>Constructor Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A reference to the constructor of a type. The constructor
 * can be used in a NewExpression. It is a subtype of the equivalent
 * ClassifierTypeRef.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.ts.typeRefs.ConstructorTypeRef#getTypeArg <em>Type Arg</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.typeRefs.ConstructorTypeRef#isConstructorRef <em>Constructor Ref</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getConstructorTypeRef()
 * @model
 * @generated
 */
public interface ConstructorTypeRef extends BaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Type Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Arg</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Arg</em>' containment reference.
	 * @see #setTypeArg(TypeArgument)
	 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getConstructorTypeRef_TypeArg()
	 * @model containment="true"
	 * @generated
	 */
	TypeArgument getTypeArg();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.ts.typeRefs.ConstructorTypeRef#getTypeArg <em>Type Arg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Arg</em>' containment reference.
	 * @see #getTypeArg()
	 * @generated
	 */
	void setTypeArg(TypeArgument value);

	/**
	 * Returns the value of the '<em><b>Constructor Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this {@link TypeTypeRef} is declared to be a reference to a constructor. This is the case if, in the
	 * source code, keyword {@code constructor} has been used instead of keyword {@code type}:
	 * <pre>
	 * type{C} // flag will be false
	 * constructor{C} // flag will be true
	 * </pre>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constructor Ref</em>' attribute.
	 * @see #setConstructorRef(boolean)
	 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getConstructorTypeRef_ConstructorRef()
	 * @model unique="false"
	 * @generated
	 */
	boolean isConstructorRef();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.ts.typeRefs.ConstructorTypeRef#isConstructorRef <em>Constructor Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constructor Ref</em>' attribute.
	 * @see #isConstructorRef()
	 * @generated
	 */
	void setConstructorRef(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _xifexpression = null;\nboolean _isConstructorRef = this.isConstructorRef();\nif (_isConstructorRef)\n{\n\t_xifexpression = \"constructor\";\n}\nelse\n{\n\t_xifexpression = \"type\";\n}\nfinal <%java.lang.String%> kwd = _xifexpression;\n<%java.lang.String%> _xifexpression_1 = null;\n<%eu.numberfour.n4js.ts.typeRefs.TypeArgument%> _typeArg = this.getTypeArg();\nboolean _tripleEquals = (null == _typeArg);\nif (_tripleEquals)\n{\n\t_xifexpression_1 = \"\";\n}\nelse\n{\n\t<%eu.numberfour.n4js.ts.typeRefs.TypeArgument%> _typeArg_1 = this.getTypeArg();\n\t_xifexpression_1 = _typeArg_1.getTypeRefAsString();\n}\nfinal <%java.lang.String%> refName = _xifexpression_1;\n<%java.lang.String%> _modifiersAsString = this.getModifiersAsString();\nreturn ((((kwd + \"{\") + refName) + \"}\") + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ConstructorTypeRef
