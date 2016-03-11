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
package eu.numberfour.n4js.external;

import static com.google.common.base.Preconditions.checkState;
import static eu.numberfour.n4js.external.libraries.ExternalLibrariesActivator.N4_NPM_FOLDER_SUPPLIER;
import static eu.numberfour.n4js.external.libraries.PackageJson.PACKAGE_JSON;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.Platform;

import com.google.inject.Singleton;

import eu.numberfour.n4js.external.libraries.ExternalLibrariesActivator;

/**
 * Target platform install location and target platform file location provider that is used when the platform is
 * running, hence the Eclipse workspace exists and available..
 */
@Singleton
public class EclipseTargetPlatformInstallLocationProvider implements TargetPlatformInstallLocationProvider {

	@Override
	public URI getTargetPlatformInstallLocation() {
		checkState(Platform.isRunning(), "Injection problem? Expected running platform.");
		final File location = N4_NPM_FOLDER_SUPPLIER.get();
		return location.toURI();
	}

	@Override
	public URI getTargetPlatformFileLocation() {
		return new File(N4_NPM_FOLDER_SUPPLIER.get(), PACKAGE_JSON).toURI();
	}

	@Override
	public URI getTargetPlatformLocalGitRepositoryLocation() {
		return ExternalLibrariesActivator.N4_GIT_FOLDER_SUPPLIER.get().toURI();
	}

}
