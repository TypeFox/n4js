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
import static com.google.common.collect.Maps.toMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.io.File.pathSeparator;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.jgit.lib.Repository;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

/**
 * Working set manager based on Git repositories.
 */
@SuppressWarnings("restriction")
public class GitRepositoryAwareWorkingSetManager extends WorkingSetManagerImpl {

	private final RepositoryCache repositoryCache;
	private final IPreferenceChangeListener repositoryChangeListener;

	/**
	 * Sole constructor for creating the working set manager. Internally initializes the cache for repositories.
	 */
	public GitRepositoryAwareWorkingSetManager() {
		repositoryCache = Activator.getDefault().getRepositoryCache();
		repositoryChangeListener = new IPreferenceChangeListener() {

			@Override
			public void preferenceChange(final PreferenceChangeEvent event) {
				if (!RepositoryUtil.PREFS_DIRECTORIES.equals(event.getKey())) {
					return;
				}

				if (!orderedWorkingSetIds.isEmpty() && !visibleWorkingSetIds.isEmpty()) {

					MapDifference<String, String> diff = calculateDifference(event);
					if (!diff.areEqual()) {

						// Deletions
						final Set<String> deletions = diff.entriesOnlyOnLeft().keySet();
						for (String deletedUrl : deletions) {
							orderedWorkingSetIds.remove(deletedUrl);
							visibleWorkingSetIds.remove(deletedUrl);
						}

						// Additions
						final Set<String> additions = diff.entriesOnlyOnRight().keySet();
						for (String addedUrl : additions) {
							orderedWorkingSetIds.add(addedUrl);
							visibleWorkingSetIds.add(addedUrl);
						}

					}

				}

				discardWorkingSetState();
				saveState(new NullProgressMonitor());

				WorkingSetManagerBroker workingSetManagerBroker = getWorkingSetManagerBroker();
				if (workingSetManagerBroker.isWorkingSetTopLevel()) {
					final WorkingSetManager activeManger = workingSetManagerBroker.getActiveManger();
					if (activeManger != null) {
						if (activeManger.getId().equals(getId())) {
							workingSetManagerBroker.refreshNavigator();
						}
					}
				}

			}

			private MapDifference<String, String> calculateDifference(PreferenceChangeEvent event) {
				String oldValue = Strings.nullToEmpty((String) event.getOldValue());
				String newValue = Strings.nullToEmpty((String) event.getNewValue());

				Map<String, String> oldMappings = toMap(newHashSet(Splitter.on(pathSeparator).split(oldValue)), i -> i);
				Map<String, String> newMappings = toMap(newHashSet(Splitter.on(pathSeparator).split(newValue)), i -> i);

				return Maps.difference(oldMappings, newMappings);

			}

		};

		final IEclipsePreferences gitNode = InstanceScope.INSTANCE.getNode(Activator.getPluginId());
		gitNode.addPreferenceChangeListener(repositoryChangeListener);

		final BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		context.addBundleListener(new BundleListener() {

			@Override
			public void bundleChanged(final BundleEvent event) {
				if (BundleEvent.STOPPING == event.getType()) {
					gitNode.removePreferenceChangeListener(repositoryChangeListener);
				}
			}

		});
	}

	@Override
	public String getLabel() {
		return "Git Repository";
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		final Collection<Repository> repositories = newArrayList(repositoryCache.getAllRepositories());
		repositories.add(null); // For 'Other Projects'.
		return newArrayList(from(repositories)
				.transform(repository -> new GitRepositoryWorkingSet(repository, this)));
	}

	/**
	 * Working set for grouping projects based on the attached local Git repository.
	 */
	public static final class GitRepositoryWorkingSet extends WorkingSetImpl {

		private final String rootUri;
		private final String name;

		private static String repositoryToId(Repository repository) {
			if (null == repository) {
				return OTHERS_WORKING_SET_ID;
			}
			return toUriString(repository.getDirectory().getParentFile().toURI());
		}

		private GitRepositoryWorkingSet(/* nullable */ final Repository repository, final WorkingSetManager manager) {
			super(repositoryToId(repository), manager);
			if (repository == null) {
				rootUri = null;
				name = OTHERS_WORKING_SET_ID;
			} else {
				final File directory = repository.getDirectory().getParentFile();
				rootUri = toUriString(directory.toURI());
				name = directory.getName();
			}
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public IAdaptable[] getElements() {
			final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			final IProject[] elements = new IProject[projects.length];
			int elementCount = 0;
			for (int i = 0, size = projects.length; i < size; i++) {
				final IProject project = projects[i];
				if (null == rootUri) {
					if (!ResourceUtil.isSharedWithGit(project)) {
						elements[elementCount++] = project;
					}
				} else {
					if (toUriString(project.getLocationURI()).startsWith(rootUri)) {
						elements[elementCount++] = project;
					}
				}
			}
			return Arrays.copyOfRange(elements, 0, elementCount);
		}

		/**
		 * Returns with the {@link URI#toString()} of the argument. Trims the trailing forward slash if any.
		 */
		private static String toUriString(final URI uri) {
			final String uriString = uri.toString();
			final int length = uriString.length();
			if (uriString.charAt(length - 1) == '/') {
				return uriString.substring(0, length - 1);
			}
			return uriString;
		}

	}

}
