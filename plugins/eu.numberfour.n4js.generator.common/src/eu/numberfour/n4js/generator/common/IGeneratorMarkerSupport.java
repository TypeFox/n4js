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
package eu.numberfour.n4js.generator.common;

import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.ImplementedBy;

/**
 * Generic Marker support for UI and non-UI cases.
 */
@ImplementedBy(IGeneratorMarkerSupport.NullGeneratorMarkersupport.class)
public interface IGeneratorMarkerSupport {

	/**
	 * Default Null-implementation
	 */
	public static class NullGeneratorMarkersupport implements IGeneratorMarkerSupport {

		@Override
		public void createMarker(Resource res, String message) {
			// n.t.d.
		}

		@Override
		public void deleteMarker(Resource res) {
			// n.t.d.
		}

		@Override
		public boolean hasMarker(Resource res) {
			return false;
		}

	}

	/** Marker type as used in plugin.xml of *.n4js.ui plug-in. */
	public static final String MARKER__EU_NUMBERFOUR_IDE_N4JS_UI_COMPILER_ERROR = "eu.numberfour.n4js.ui.compiler.error";

	/** Create an marker */
	public void createMarker(Resource res, String message);

	/** Delete an existing marker - if present. */
	public void deleteMarker(Resource res);

	/** @return true if marker is present. */
	public boolean hasMarker(Resource res);

}
