/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package eu.numberfour.n4js.ts.types.impl;

import eu.numberfour.n4js.ts.types.TAnnotableElement;
import eu.numberfour.n4js.ts.types.TAnnotation;
import eu.numberfour.n4js.ts.types.TModule;
import eu.numberfour.n4js.ts.types.TVariable;
import eu.numberfour.n4js.ts.types.Type;
import eu.numberfour.n4js.ts.types.TypesPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TModule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getVendorID <em>Vendor ID</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getProjectArtifactId <em>Project Artifact Id</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getModuleLoader <em>Module Loader</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#isStaticPolyfillModule <em>Static Polyfill Module</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#isStaticPolyfillAware <em>Static Polyfill Aware</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#isMainModule <em>Main Module</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#isPreLinkingPhase <em>Pre Linking Phase</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getTopLevelTypes <em>Top Level Types</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getInternalTypes <em>Internal Types</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getExposedInternalTypes <em>Exposed Internal Types</em>}</li>
 *   <li>{@link eu.numberfour.n4js.ts.types.impl.TModuleImpl#getModuleSpecifier <em>Module Specifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TModuleImpl extends SyntaxRelatedTElementImpl implements TModule {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<TAnnotation> annotations;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVendorID() <em>Vendor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorID()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendorID() <em>Vendor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorID()
	 * @generated
	 * @ordered
	 */
	protected String vendorID = VENDOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectArtifactId() <em>Project Artifact Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectArtifactId()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_ARTIFACT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectArtifactId() <em>Project Artifact Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectArtifactId()
	 * @generated
	 * @ordered
	 */
	protected String projectArtifactId = PROJECT_ARTIFACT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getModuleLoader() <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleLoader()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_LOADER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleLoader() <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleLoader()
	 * @generated
	 * @ordered
	 */
	protected String moduleLoader = MODULE_LOADER_EDEFAULT;

	/**
	 * The default value of the '{@link #isStaticPolyfillModule() <em>Static Polyfill Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillModule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_POLYFILL_MODULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStaticPolyfillModule() <em>Static Polyfill Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillModule()
	 * @generated
	 * @ordered
	 */
	protected boolean staticPolyfillModule = STATIC_POLYFILL_MODULE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStaticPolyfillAware() <em>Static Polyfill Aware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillAware()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_POLYFILL_AWARE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStaticPolyfillAware() <em>Static Polyfill Aware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillAware()
	 * @generated
	 * @ordered
	 */
	protected boolean staticPolyfillAware = STATIC_POLYFILL_AWARE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMainModule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAIN_MODULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMainModule()
	 * @generated
	 * @ordered
	 */
	protected boolean mainModule = MAIN_MODULE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPreLinkingPhase() <em>Pre Linking Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreLinkingPhase()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRE_LINKING_PHASE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPreLinkingPhase() <em>Pre Linking Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreLinkingPhase()
	 * @generated
	 * @ordered
	 */
	protected boolean preLinkingPhase = PRE_LINKING_PHASE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTopLevelTypes() <em>Top Level Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopLevelTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> topLevelTypes;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> variables;

	/**
	 * The cached value of the '{@link #getInternalTypes() <em>Internal Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> internalTypes;

	/**
	 * The cached value of the '{@link #getExposedInternalTypes() <em>Exposed Internal Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExposedInternalTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> exposedInternalTypes;

	/**
	 * The default value of the '{@link #getModuleSpecifier() <em>Module Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifier()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_SPECIFIER_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TAnnotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<TAnnotation>(TAnnotation.class, this, TypesPackage.TMODULE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendorID() {
		return vendorID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendorID(String newVendorID) {
		String oldVendorID = vendorID;
		vendorID = newVendorID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__VENDOR_ID, oldVendorID, vendorID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectArtifactId() {
		return projectArtifactId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectArtifactId(String newProjectArtifactId) {
		String oldProjectArtifactId = projectArtifactId;
		projectArtifactId = newProjectArtifactId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__PROJECT_ARTIFACT_ID, oldProjectArtifactId, projectArtifactId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModuleLoader() {
		return moduleLoader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleLoader(String newModuleLoader) {
		String oldModuleLoader = moduleLoader;
		moduleLoader = newModuleLoader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__MODULE_LOADER, oldModuleLoader, moduleLoader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStaticPolyfillModule() {
		return staticPolyfillModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaticPolyfillModule(boolean newStaticPolyfillModule) {
		boolean oldStaticPolyfillModule = staticPolyfillModule;
		staticPolyfillModule = newStaticPolyfillModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__STATIC_POLYFILL_MODULE, oldStaticPolyfillModule, staticPolyfillModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStaticPolyfillAware() {
		return staticPolyfillAware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaticPolyfillAware(boolean newStaticPolyfillAware) {
		boolean oldStaticPolyfillAware = staticPolyfillAware;
		staticPolyfillAware = newStaticPolyfillAware;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__STATIC_POLYFILL_AWARE, oldStaticPolyfillAware, staticPolyfillAware));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMainModule() {
		return mainModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMainModule(boolean newMainModule) {
		boolean oldMainModule = mainModule;
		mainModule = newMainModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__MAIN_MODULE, oldMainModule, mainModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPreLinkingPhase() {
		return preLinkingPhase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLinkingPhase(boolean newPreLinkingPhase) {
		boolean oldPreLinkingPhase = preLinkingPhase;
		preLinkingPhase = newPreLinkingPhase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__PRE_LINKING_PHASE, oldPreLinkingPhase, preLinkingPhase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getTopLevelTypes() {
		if (topLevelTypes == null) {
			topLevelTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__TOP_LEVEL_TYPES);
		}
		return topLevelTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TVariable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.TMODULE__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getInternalTypes() {
		if (internalTypes == null) {
			internalTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__INTERNAL_TYPES);
		}
		return internalTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getExposedInternalTypes() {
		if (exposedInternalTypes == null) {
			exposedInternalTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES);
		}
		return exposedInternalTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModuleSpecifier() {
		return this.getQualifiedName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return ((InternalEList<?>)getTopLevelTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return ((InternalEList<?>)getInternalTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return ((InternalEList<?>)getExposedInternalTypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				return getQualifiedName();
			case TypesPackage.TMODULE__VENDOR_ID:
				return getVendorID();
			case TypesPackage.TMODULE__PROJECT_ARTIFACT_ID:
				return getProjectArtifactId();
			case TypesPackage.TMODULE__MODULE_LOADER:
				return getModuleLoader();
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				return isStaticPolyfillModule();
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				return isStaticPolyfillAware();
			case TypesPackage.TMODULE__MAIN_MODULE:
				return isMainModule();
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				return isPreLinkingPhase();
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return getTopLevelTypes();
			case TypesPackage.TMODULE__VARIABLES:
				return getVariables();
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return getInternalTypes();
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return getExposedInternalTypes();
			case TypesPackage.TMODULE__MODULE_SPECIFIER:
				return getModuleSpecifier();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends TAnnotation>)newValue);
				return;
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
				return;
			case TypesPackage.TMODULE__VENDOR_ID:
				setVendorID((String)newValue);
				return;
			case TypesPackage.TMODULE__PROJECT_ARTIFACT_ID:
				setProjectArtifactId((String)newValue);
				return;
			case TypesPackage.TMODULE__MODULE_LOADER:
				setModuleLoader((String)newValue);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				setStaticPolyfillModule((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				setStaticPolyfillAware((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__MAIN_MODULE:
				setMainModule((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				setPreLinkingPhase((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				getTopLevelTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TMODULE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				getInternalTypes().clear();
				getInternalTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				getExposedInternalTypes().clear();
				getExposedInternalTypes().addAll((Collection<? extends Type>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
				return;
			case TypesPackage.TMODULE__VENDOR_ID:
				setVendorID(VENDOR_ID_EDEFAULT);
				return;
			case TypesPackage.TMODULE__PROJECT_ARTIFACT_ID:
				setProjectArtifactId(PROJECT_ARTIFACT_ID_EDEFAULT);
				return;
			case TypesPackage.TMODULE__MODULE_LOADER:
				setModuleLoader(MODULE_LOADER_EDEFAULT);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				setStaticPolyfillModule(STATIC_POLYFILL_MODULE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				setStaticPolyfillAware(STATIC_POLYFILL_AWARE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__MAIN_MODULE:
				setMainModule(MAIN_MODULE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				setPreLinkingPhase(PRE_LINKING_PHASE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				return;
			case TypesPackage.TMODULE__VARIABLES:
				getVariables().clear();
				return;
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				getInternalTypes().clear();
				return;
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				getExposedInternalTypes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
			case TypesPackage.TMODULE__VENDOR_ID:
				return VENDOR_ID_EDEFAULT == null ? vendorID != null : !VENDOR_ID_EDEFAULT.equals(vendorID);
			case TypesPackage.TMODULE__PROJECT_ARTIFACT_ID:
				return PROJECT_ARTIFACT_ID_EDEFAULT == null ? projectArtifactId != null : !PROJECT_ARTIFACT_ID_EDEFAULT.equals(projectArtifactId);
			case TypesPackage.TMODULE__MODULE_LOADER:
				return MODULE_LOADER_EDEFAULT == null ? moduleLoader != null : !MODULE_LOADER_EDEFAULT.equals(moduleLoader);
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				return staticPolyfillModule != STATIC_POLYFILL_MODULE_EDEFAULT;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				return staticPolyfillAware != STATIC_POLYFILL_AWARE_EDEFAULT;
			case TypesPackage.TMODULE__MAIN_MODULE:
				return mainModule != MAIN_MODULE_EDEFAULT;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				return preLinkingPhase != PRE_LINKING_PHASE_EDEFAULT;
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return topLevelTypes != null && !topLevelTypes.isEmpty();
			case TypesPackage.TMODULE__VARIABLES:
				return variables != null && !variables.isEmpty();
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return internalTypes != null && !internalTypes.isEmpty();
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return exposedInternalTypes != null && !exposedInternalTypes.isEmpty();
			case TypesPackage.TMODULE__MODULE_SPECIFIER:
				return MODULE_SPECIFIER_EDEFAULT == null ? getModuleSpecifier() != null : !MODULE_SPECIFIER_EDEFAULT.equals(getModuleSpecifier());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TAnnotableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMODULE__ANNOTATIONS: return TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TAnnotableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS: return TypesPackage.TMODULE__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (qualifiedName: ");
		result.append(qualifiedName);
		result.append(", vendorID: ");
		result.append(vendorID);
		result.append(", projectArtifactId: ");
		result.append(projectArtifactId);
		result.append(", moduleLoader: ");
		result.append(moduleLoader);
		result.append(", staticPolyfillModule: ");
		result.append(staticPolyfillModule);
		result.append(", staticPolyfillAware: ");
		result.append(staticPolyfillAware);
		result.append(", mainModule: ");
		result.append(mainModule);
		result.append(", preLinkingPhase: ");
		result.append(preLinkingPhase);
		result.append(')');
		return result.toString();
	}

} //TModuleImpl
