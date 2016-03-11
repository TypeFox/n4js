/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package eu.numberfour.n4js.tests.compare

import com.google.inject.Inject
import eu.numberfour.n4js.ApiImplCompareTestHelper
import eu.numberfour.n4js.N4JSUiInjectorProvider
import eu.numberfour.n4js.compare.ProjectCompareHelper
import eu.numberfour.n4js.ui.compare.ProjectCompareTreeHelper
import eu.numberfour.n4js.n4mf.N4mfFactory
import java.io.File
import org.eclipse.core.resources.IProject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.ui.XtextProjectHelper
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.addNature

import static extension eu.numberfour.n4js.tests.util.ProjectUtils.*

/**
 * Tests for the API / implementation compare logic (not for the UI part!).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class ApiImplComparePluginUITest extends AbstractApiImplCompareTest {

	@Inject
	private ProjectCompareHelper projectCompareHelper;
	@Inject
	private ProjectCompareTreeHelper projectCompareTreeHelper;
	@Inject
	private extension ApiImplCompareTestHelper;


	@BeforeClass
	public static def void setupEclipseWorkspace() {
		importTestProject(PROJECT_ID_UTILS)
		importTestProject(PROJECT_ID_API)
		importTestProject(PROJECT_ID_IMPL)
	}


	// note: main test contained in super class (common for UI and headless cases)


	// some tests only applicable when running in UI mode:

	@Test
	public def void testApiNoteDocumentation() {
		val errMsgs = newArrayList
		val comparison = projectCompareHelper.createComparison(true,errMsgs)
		assertEquals(0, errMsgs.size)

		// checking @apiNote documentation

		val doc = projectCompareTreeHelper.readDocumentation(comparison, #[0]);

		val clazz = comparison.findEntryForType("x.y.M.Clazz")
		assertNotNull(clazz)
		assertEquals("Clazz",clazz.elementNameForEntry)
		assertEquals(
			"documentation for class Clazz was not correctly derived from JSdoc tag @apiNote",
			"hello from API! | hello from implementation!",
			doc.get(clazz))
	}


	@Test
	public def void testMultipleImplementationsWithSameId() {
		val clashIdApi = "eu.numberfour.clash.api";
		val IProject pApi = createJSProject(clashIdApi)
		val IProject pImpl1 = createJSProject("eu.numberfour.clash.n4js","src","src-gen",[pd|
			if (null === pd.implementedProjects) {
				pd.implementedProjects = N4mfFactory.eINSTANCE.createImplementedProjects;
			}
			pd.implementationId = "impl.n4js"
			pd.implementedProjects.implementedProjects += createProjectReference(clashIdApi)
		])
		val IProject pImpl2 = createJSProject("eu.numberfour.clash.ios","src","src-gen",[pd|
			if (null === pd.implementedProjects) {
				pd.implementedProjects = N4mfFactory.eINSTANCE.createImplementedProjects;
			}
			pd.implementationId = "impl.n4js" // n.b.: same implementation id!
			pd.implementedProjects.implementedProjects += createProjectReference(clashIdApi)
		])
		pApi.configureProjectWithXtext
		pImpl1.configureProjectWithXtext
		pImpl2.configureProjectWithXtext

		waitForAutoBuild

		val errMsgs = newArrayList
		projectCompareHelper.createComparison(true,errMsgs)

		assertEquals(1, errMsgs.size)
		assertEquals(
			"several projects define an implementation for API project '"+clashIdApi+"' with implementation ID 'impl.n4js':\n" +
			" - eu.numberfour.clash.ios\n" +
			" - eu.numberfour.clash.n4js", errMsgs.get(0))

		pImpl2.deleteProject
		pImpl1.deleteProject
		pApi.deleteProject

		waitForAutoBuild
	}


	/**
	 * Imports a project from the probands/ApiImplCompare folder
	 */
	private static def IProject importTestProject(String name) {
		val project = importProject(new File("probands/ApiImplCompare"), name);
		addNature(project, XtextProjectHelper.NATURE_ID);
		waitForAutoBuild
		assertMarkers("imported test project '"+name+"' should have no errors/warnings", project, 0)
		return project;
	}
}
