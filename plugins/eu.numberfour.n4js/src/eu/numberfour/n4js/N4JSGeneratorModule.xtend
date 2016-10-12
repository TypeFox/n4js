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
package eu.numberfour.n4js

import com.google.inject.Binder
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.Grammar

/**
 *
 */
class N4JSGeneratorModule extends DefaultGeneratorModule {
	
	def configureXtextGeneratorNaming(Binder binder) {
		binder.bind(XtextGeneratorNaming).to(N4JSGeneratorNaming)
	}
}

class N4JSGeneratorNaming extends XtextGeneratorNaming {
	
	override getGenericIdeBasePackage(Grammar grammar) {
		super.getEclipsePluginBasePackage(grammar)
	}
}