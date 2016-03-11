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
package eu.numberfour.n4js;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 */
@RunWith(value = Suite.class)
@SuiteClasses({
	eu.numberfour.n4js.dirtystate.AllTests.class,
	eu.numberfour.n4js.tests.contentAssist.AllTests.class,
	eu.numberfour.n4js.tests.manifest.AllTests.class,
	eu.numberfour.n4js.tests.projectModel.AllTests.class,
	eu.numberfour.n4js.tests.realworld.AllTests.class,
	eu.numberfour.n4js.tests.scoping.AllTests.class,
})
public class AllPluginTests {
	// nothing to do
}
