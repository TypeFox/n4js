/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.jsdoc.dom;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Full Member Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Full reference to a member.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.jsdoc.dom.FullMemberReference#getMemberName <em>Member Name</em>}</li>
 *   <li>{@link eu.numberfour.n4js.jsdoc.dom.FullMemberReference#isStaticMember <em>Static Member</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getFullMemberReference()
 * @model
 * @generated
 */
public interface FullMemberReference extends FullTypeReference {
	/**
	 * Returns the value of the '<em><b>Member Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Name</em>' attribute.
	 * @see #setMemberName(String)
	 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getFullMemberReference_MemberName()
	 * @model unique="false"
	 * @generated
	 */
	String getMemberName();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.jsdoc.dom.FullMemberReference#getMemberName <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member Name</em>' attribute.
	 * @see #getMemberName()
	 * @generated
	 */
	void setMemberName(String value);

	/**
	 * Returns the value of the '<em><b>Static Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Static Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Static Member</em>' attribute.
	 * @see #setStaticMember(boolean)
	 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getFullMemberReference_StaticMember()
	 * @model unique="false"
	 * @generated
	 */
	boolean isStaticMember();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.jsdoc.dom.FullMemberReference#isStaticMember <em>Static Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Member</em>' attribute.
	 * @see #isStaticMember()
	 * @generated
	 */
	void setStaticMember(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _and = false;\n<%java.lang.String%> _memberName = this.getMemberName();\nboolean _tripleNotEquals = (_memberName != null);\nif (!_tripleNotEquals)\n{\n\t_and = false;\n} else\n{\n\t<%java.lang.String%> _memberName_1 = this.getMemberName();\n\tboolean _isEmpty = _memberName_1.isEmpty();\n\tboolean _not = (!_isEmpty);\n\t_and = _not;\n}\nreturn _and;'"
	 * @generated
	 */
	boolean memberNameSet();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _string = super.toString();\n<%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>(_string);\nboolean _memberNameSet = this.memberNameSet();\nif (_memberNameSet)\n{\n\tint _length = strb.length();\n\tboolean _greaterThan = (_length > 0);\n\tif (_greaterThan)\n\t{\n\t\tboolean _isStaticMember = this.isStaticMember();\n\t\tif (_isStaticMember)\n\t\t{\n\t\t\tstrb.append(\"#\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\tstrb.append(\".\");\n\t\t}\n\t}\n\t<%java.lang.String%> _memberName = this.getMemberName();\n\tstrb.append(_memberName);\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String toString();

} // FullMemberReference
