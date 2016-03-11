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
package eu.numberfour.n4js.ui.building;

import org.eclipse.core.resources.IFile;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;

/**
 * A file system access implementation that completely disables trace file support to avoid all the unnecessary overhead
 * related to refresh operations on the Eclipse resource tree.
 */
public class FileSystemAccessWithoutTraceFileSupport extends EclipseResourceFileSystemAccess2 {

	@Override
	protected IFile getTraceFile(IFile file) {
		return null;
	}

}
