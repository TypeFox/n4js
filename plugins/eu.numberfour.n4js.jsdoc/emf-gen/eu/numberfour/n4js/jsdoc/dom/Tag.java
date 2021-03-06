/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.jsdoc.dom;

import eu.numberfour.n4js.jsdoc.ITagDefinition;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}</li>
 *   <li>{@link eu.numberfour.n4js.jsdoc.dom.Tag#getValues <em>Values</em>}</li>
 *   <li>{@link eu.numberfour.n4js.jsdoc.dom.Tag#getTagDefinition <em>Tag Definition</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getTag()
 * @model abstract="true"
 * @generated
 */
public interface Tag extends DocletElement {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link eu.numberfour.n4js.jsdoc.dom.TagTitle#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference.
	 * @see #setTitle(TagTitle)
	 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getTag_Title()
	 * @see eu.numberfour.n4js.jsdoc.dom.TagTitle#getTag
	 * @model opposite="tag" containment="true"
	 * @generated
	 */
	TagTitle getTitle();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' containment reference.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(TagTitle value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link eu.numberfour.n4js.jsdoc.dom.TagValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getTag_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<TagValue> getValues();

	/**
	 * Returns the value of the '<em><b>Tag Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag Definition</em>' attribute.
	 * @see #setTagDefinition(ITagDefinition)
	 * @see eu.numberfour.n4js.jsdoc.dom.DomPackage#getTag_TagDefinition()
	 * @model unique="false" dataType="eu.numberfour.n4js.jsdoc.dom.TagDefinition" transient="true"
	 * @generated
	 */
	ITagDefinition getTagDefinition();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.jsdoc.dom.Tag#getTagDefinition <em>Tag Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag Definition</em>' attribute.
	 * @see #getTagDefinition()
	 * @generated
	 */
	void setTagDefinition(ITagDefinition value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns first TagValue with given key or null, if no such key is found.
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.jsdoc.dom.TagValue%>> _values = this.getValues();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%eu.numberfour.n4js.jsdoc.dom.TagValue%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%eu.numberfour.n4js.jsdoc.dom.TagValue%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%eu.numberfour.n4js.jsdoc.dom.TagValue%> it)\n\t{\n\t\t<%java.lang.String%> _key = it.getKey();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_key, theKey));\n\t}\n};\nreturn <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%eu.numberfour.n4js.jsdoc.dom.TagValue%>>findFirst(_values, _function);'"
	 * @generated
	 */
	TagValue getValueByKey(String theKey);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%eu.numberfour.n4js.jsdoc.JSDocSerializer%>.toJSDocString(this);'"
	 * @generated
	 */
	String toString();

} // Tag
