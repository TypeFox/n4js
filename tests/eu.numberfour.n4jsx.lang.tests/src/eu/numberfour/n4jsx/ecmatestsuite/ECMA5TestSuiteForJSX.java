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
package eu.numberfour.n4jsx.ecmatestsuite;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.resource.XtextResourceSet;

import eu.numberfour.n4js.JSLibSingleTestConfig;
import eu.numberfour.n4js.analysis.Analyser;
import eu.numberfour.n4js.n4JS.Script;
import eu.numberfour.n4js.tests.ecmatestsuite.ECMA5TestSuite;
import eu.numberfour.n4jsx.tests.helper.N4JSXInjectorProvider;

/**
 *
 */
@InjectWith(N4JSXInjectorProvider.class)
public class ECMA5TestSuiteForJSX extends ECMA5TestSuite {

	@Override
	protected Script doParse(String code, URI uri, XtextResourceSet resourceSet, Analyser analyser) throws Exception {
		if (isStrictModeTestCase(code)) {
			uri = uri.trimFileExtension().appendFileExtension("n4jsx");
		} else {
			uri = uri.trimFileExtension().appendFileExtension("jsx");
		}
		// ensure code contains JSX syntax to fail in case of wrong
		// configuration
		if (!analyser.isNegative()) {
			code = code + "\nvar x42 = <div/>\n";
		}
		return parserN4JS.parse(code, uri, resourceSet);
	}

	/**
	 * @param config
	 */
	public ECMA5TestSuiteForJSX(JSLibSingleTestConfig config) {
		super(config);
	}

}