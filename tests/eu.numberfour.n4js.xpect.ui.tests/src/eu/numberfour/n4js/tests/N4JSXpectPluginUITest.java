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
package eu.numberfour.n4js.tests;

import org.junit.runner.RunWith;
import org.xpect.XpectImport;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.LinkingTest;
import org.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.xpect.xtext.lib.tests.ValidationTest;

import eu.numberfour.n4js.ui.xpect.OrganizeImportXpectMethod;
import eu.numberfour.n4js.ui.xpect.OutlineXpectMethod;
import eu.numberfour.n4js.xpect.ContentAssistXpectMethod;
import eu.numberfour.n4js.xpect.HyperlinkXpectMethod;
import eu.numberfour.n4js.xpect.ProposalXpectMethod;
import eu.numberfour.n4js.xpect.QuickFixXpectMethod;
import eu.numberfour.n4js.xpect.TypeXpectMethod;
import eu.numberfour.n4js.xpect.scoping.ScopeXpectMethod;
import eu.numberfour.n4js.xpect.validation.NoerrorsXpectMethod;
import eu.numberfour.n4js.xpect.validation.suppression.SuppressIssuesSetup;

/**
 * Plugin linking test
 */
@XpectSuiteClasses({
		LinkingTest.class,
		TypeXpectMethod.class,
		NoerrorsXpectMethod.class,
		ScopeXpectMethod.class,
		ResourceDescriptionTest.class,
		ValidationTest.class,
		OutlineXpectMethod.class,
		QuickFixXpectMethod.class,
		ContentAssistXpectMethod.class,
		ProposalXpectMethod.class,
		HyperlinkXpectMethod.class,
		OrganizeImportXpectMethod.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "model_ui", fileExtensions = "xt")
@XpectImport(SuppressIssuesSetup.class)
public class N4JSXpectPluginUITest {
	//
}
