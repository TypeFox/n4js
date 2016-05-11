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
package eu.numberfour.n4js.ui.workingsets;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static eu.numberfour.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

/**
 * Manager for project location aware working sets.
 */
@SuppressWarnings("restriction")
public class ProjectLocationAwareWorkingSetManager extends WorkingSetManagerImpl {

	private static final Path WS_ROOT_PATH = getWorkspace().getRoot().getLocation().toFile().toPath();

	private final Multimap<String, IProject> projectLocations;

	/**
	 * Sole constructor for creating a new working set manager instance.
	 */
	public ProjectLocationAwareWorkingSetManager() {
		projectLocations = initProjectLocation();
	}

	@Override
	public String getLabel() {
		return "Project Location";
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		return newArrayList(from(projectLocations.keySet()).transform(id -> new ProjectLocationWorkingSet(id, this)));
	}

	private Multimap<String, IProject> initProjectLocation() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject[] projects = root.getProjects();
		final Multimap<String, IProject> locations = HashMultimap.create();
		for (final IProject project : projects) {
			final String pair = getWorkingSetId(project);
			locations.put(pair, project);
		}
		return locations;
	}

	private String getWorkingSetId(IProject project) {
		Path projectPath = project.getLocation().toFile().toPath();
		Path parentPath = projectPath.getParent();
		if (WS_ROOT_PATH.equals(parentPath)) {
			return OTHERS_WORKING_SET_ID;
		}

		if (parentPath.startsWith(WS_ROOT_PATH)) {
			return parentPath.toFile().getName();
		}

		final Collection<Path> repositoryPaths = from(asList(getRepositoryCache().getAllRepositories()))
				.transform(r -> r.getDirectory().getParentFile().toPath()).toSet();

		for (Path repositoryPath : repositoryPaths) {
			if (repositoryPath.equals(projectPath)) {
				return projectPath.toFile().getName();
			} else if (projectPath.startsWith(repositoryPath)) {
				return parentPath.toFile().getName();
			}
		}

		return OTHERS_WORKING_SET_ID;
	}

	private RepositoryCache getRepositoryCache() {
		return Activator.getDefault().getRepositoryCache();
	}

	/**
	 * Working set for projects based on their location.
	 */
	public static final class ProjectLocationWorkingSet extends WorkingSetImpl {

		private ProjectLocationWorkingSet(String id, ProjectLocationAwareWorkingSetManager manager) {
			super(id, manager);
		}

		@Override
		public ProjectLocationAwareWorkingSetManager getWorkingSetManager() {
			return (ProjectLocationAwareWorkingSetManager) super.getWorkingSetManager();
		}

		@Override
		public IAdaptable[] getElements() {
			return Iterables.toArray(getWorkingSetManager().projectLocations.get(getId()), IAdaptable.class);
		}

	}

}
