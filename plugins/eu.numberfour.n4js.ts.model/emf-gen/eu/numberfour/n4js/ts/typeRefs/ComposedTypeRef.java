/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.ts.typeRefs;

import eu.numberfour.n4js.ts.types.TMember;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for type references containing an ordered set of other types, that is
 * {@link UnionTypeExpression} and {@link IntersectionTypeExpression}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.ts.typeRefs.ComposedTypeRef#getCachedComposedMembers <em>Cached Composed Members</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.typeRefs.ComposedTypeRef#getOriginalComposedTypeRef <em>Original Composed Type Ref</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefs <em>Type Refs</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface ComposedTypeRef extends StaticBaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Cached Composed Members</b></em>' containment reference list.
	 * The list contents are of type {@link eu.numberfour.n4js.ts.types.TMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * These are the members that can be accessed via property access on the union/intersection
	 * type directly, because they are common to all types contained in the ComposedTypeRef.
	 * This cache will be filled lazily by ComposedMemberScope, so client code should
	 * usually not assume this information is complete and best not use it at all.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cached Composed Members</em>' containment reference list.
	 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef_CachedComposedMembers()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<TMember> getCachedComposedMembers();

	/**
	 * Returns the value of the '<em><b>Original Composed Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * During type variable substitution, type references may be copied. Whenever this happens
	 * to a ComposedTypeRef, Xsemantics rule 'substTypeVariablesInComposedTypeRef' will let this
	 * property point to the original ComposedTypeRef. This is required for caching composed
	 * members, see {@link ComposedMemberScope#getCacheHolder(ComposedTypeRef)}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Original Composed Type Ref</em>' reference.
	 * @see #setOriginalComposedTypeRef(ComposedTypeRef)
	 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef_OriginalComposedTypeRef()
	 * @model transient="true"
	 * @generated
	 */
	ComposedTypeRef getOriginalComposedTypeRef();

	/**
	 * Sets the value of the '{@link eu.numberfour.n4js.ts.typeRefs.ComposedTypeRef#getOriginalComposedTypeRef <em>Original Composed Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Composed Type Ref</em>' reference.
	 * @see #getOriginalComposedTypeRef()
	 * @generated
	 */
	void setOriginalComposedTypeRef(ComposedTypeRef value);

	/**
	 * Returns the value of the '<em><b>Type Refs</b></em>' containment reference list.
	 * The list contents are of type {@link eu.numberfour.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Refs</em>' containment reference list.
	 * @see eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef_TypeRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeRef> getTypeRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Union type cannot be marked as dynamic
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isDynamic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}, only returns list of composed members, to be called by subclass.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%eu.numberfour.n4js.ts.typeRefs.TypeRef%>> _typeRefs = this.getTypeRefs();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%eu.numberfour.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%eu.numberfour.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>>()\n{\n\tpublic <%java.lang.String%> apply(final <%eu.numberfour.n4js.ts.typeRefs.TypeRef%> it)\n\t{\n\t\treturn it.getTypeRefAsString();\n\t}\n};\n<%org.eclipse.emf.common.util.EList%><<%java.lang.String%>> _map = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%eu.numberfour.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>>map(_typeRefs, _function);\n<%java.lang.String%> _join = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(_map, \",\");\n<%java.lang.String%> _plus = (\"{\" + _join);\n<%java.lang.String%> _plus_1 = (_plus + \"}\");\n<%java.lang.String%> _modifiersAsString = this.getModifiersAsString();\nreturn (_plus_1 + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ComposedTypeRef
