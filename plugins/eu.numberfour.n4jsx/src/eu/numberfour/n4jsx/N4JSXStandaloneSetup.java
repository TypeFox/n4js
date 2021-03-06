/*
 * generated by Xtext
 */
package eu.numberfour.n4jsx;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.google.inject.Injector;

import eu.numberfour.n4js.n4JS.N4JSPackage;
import eu.numberfour.n4js.n4mf.N4mfPackage;
import eu.numberfour.n4js.ts.typeRefs.TypeRefsPackage;
import eu.numberfour.n4js.ts.types.TypesPackage;
import eu.numberfour.n4jsx.n4JSX.N4JSXPackage;

/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class N4JSXStandaloneSetup extends N4JSXStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registers in the stand-alone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	/**
	 * Same as {@link #doSetup()}, but won't invoke {@code N4JSStandaloneSetup#doSetup()}. For details, see
	 * {@link #createInjectorAndDoEMFRegistrationWithoutParentLanguages()}.
	 */
	public static void doSetupWithoutParentLanguages() {
		new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistrationWithoutParentLanguages();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		TypeRefsPackage.eINSTANCE.getNsURI();
		TypesPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		N4mfPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();
		N4JSXPackage.eINSTANCE.getNsURI();

		return super.createInjectorAndDoEMFRegistration();
	}

	/**
	 * Same as {@link #createInjectorAndDoEMFRegistration()}, but won't invoke {@code N4JSStandaloneSetup#doSetup()}.
	 * <p>
	 * The default Xtext behavior of {@link #createInjectorAndDoEMFRegistration()} is to invoke {@link #doSetup()} on
	 * the parent language(s), which is N4JS in our case. This method performs all registration for N4JSX <b>without</b>
	 * triggering such a setup of N4JS.
	 * <p>
	 * <b>This method assumes that the setup of N4JS has already taken place.</b>
	 */
	public Injector createInjectorAndDoEMFRegistrationWithoutParentLanguages() {
		// trigger class loading
		TypeRefsPackage.eINSTANCE.getNsURI();
		TypesPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		N4mfPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();
		N4JSXPackage.eINSTANCE.getNsURI();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}
}
