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
package eu.numberfour.n4js.ui.utils;

import static eu.numberfour.n4js.ui.internal.N4jsActivator.EU_NUMBERFOUR_N4JS_N4JS;

import com.google.inject.Injector;

import eu.numberfour.n4js.ui.internal.N4jsActivator;
import eu.numberfour.n4js.utils.injector.InjectorSupplier;

/**
 * Supplies the injector for the N4JS language.
 */
public class N4JSInjectorSupplier implements InjectorSupplier {

	@Override
	public Injector get() {
		return N4jsActivator.getInstance().getInjector(getInjectorId());
	}

	@Override
	public String getInjectorId() {
		return EU_NUMBERFOUR_N4JS_N4JS;
	}

}
