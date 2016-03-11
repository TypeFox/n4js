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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_AUTO_REFRESH;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_MANUAL_BUILD;
import static org.eclipse.core.resources.ResourcesPlugin.FAMILY_MANUAL_REFRESH;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import com.google.inject.Inject;

/**
 * Provides a workspace job to clients that can be run to perform external project clean/build. This workspace job is
 * required to avoid any deadlocks on the UI thread via the proper {@link ISchedulingRule workspace scheduling rule}
 * usage. This class should be used for instance from a {@link IResourceChangeListener} implementation, when we are in
 * the middle of a workspace modification and we would like to avoid any deadlocks.
 */
public class ExternalLibraryBuildJobProvider {

	@Inject
	private ExternalLibraryBuilderHelper builderHelper;

	/**
	 * Creates a new build job that cleans and builds the given external projects.
	 *
	 * @param toBuild
	 *            the projects that has to be build.
	 * @param toClean
	 *            the projects that has to be cleaned.
	 * @return a job for building and cleaning the projects.
	 */
	public Job createBuildJob(final Iterable<IProject> toBuild, final Iterable<IProject> toClean) {
		return new ExternalLibraryBuildJob(builderHelper, toBuild, toClean);
	}

	/* default */ static class ExternalLibraryBuildJob extends Job {

		private final Iterable<IProject> toBuild;
		private final Iterable<IProject> toClean;
		private final ExternalLibraryBuilderHelper builderHelper;

		private ExternalLibraryBuildJob(final ExternalLibraryBuilderHelper builderHelper,
				final Iterable<IProject> toBuild, final Iterable<IProject> toClean) {

			super("External library build");
			this.builderHelper = checkNotNull(builderHelper, "builderHelper");
			this.toBuild = checkNotNull(toBuild, "toBuild");
			this.toClean = checkNotNull(toClean, "toClean");
			setUser(true);
			setSystem(false);
			setPriority(SHORT);
			setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		}

		@Override
		public boolean belongsTo(final Object family) {
			return FAMILY_AUTO_BUILD.equals(family)
					|| FAMILY_AUTO_REFRESH.equals(family)
					|| FAMILY_MANUAL_BUILD.equals(family)
					|| FAMILY_MANUAL_REFRESH.equals(family);
		}

		@Override
		public IStatus run(final IProgressMonitor monitor) {

			monitor.beginTask("Building external libraries...", IProgressMonitor.UNKNOWN);

			for (final IProject project : toClean) {
				builderHelper.clean(project, monitor);
			}
			for (final IProject project : toBuild) {
				builderHelper.build(project, monitor);
			}

			return Status.OK_STATUS;
		}

	}

}
