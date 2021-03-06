/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotable Script Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.n4JS.AnnotableScriptElement#getAnnotationList <em>Annotation List</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getAnnotableScriptElement()
 * @model abstract="true"
 * @generated
 */
public interface AnnotableScriptElement extends AnnotableElement, ScriptElement {
	/**
	 * Returns the value of the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotation List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotation List</em>' containment reference.
	 * @see #setAnnotationList(AnnotationList)
	 * @see eu.numberfour.n4js.n4JS.N4JSPackage#getAnnotableScriptElement_AnnotationList()
	 * @model containment="true"
	 * @generated
	 */
	AnnotationList getAnnotationList();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.n4JS.AnnotableScriptElement#getAnnotationList <em>Annotation List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotation List</em>' containment reference.
	 * @see #getAnnotationList()
	 * @generated
	 */
	void setAnnotationList(AnnotationList value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.n4JS.Annotation%>> _elvis = null;\n<%eu.numberfour.n4js.n4JS.AnnotationList%> _annotationList = this.getAnnotationList();\n<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.n4JS.Annotation%>> _annotations = null;\nif (_annotationList!=null)\n{\n\t_annotations=_annotationList.getAnnotations();\n}\nif (_annotations != null)\n{\n\t_elvis = _annotations;\n} else\n{\n\t<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.n4JS.Annotation%>> _emptyEList = <%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%>.<<%eu.numberfour.n4js.n4JS.Annotation%>>emptyEList();\n\t_elvis = _emptyEList;\n}\nreturn _elvis;'"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

} // AnnotableScriptElement
