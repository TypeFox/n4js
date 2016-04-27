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
package eu.numberfour.n4js.ui;

import static com.google.common.base.Optional.absent;
import static java.io.File.separator;
import static org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import eu.numberfour.n4js.n4mf.ProjectType;
import eu.numberfour.n4js.ui.internal.N4JSActivator;

/**
 * Cache for {@link ImageDescriptor image descriptor}s.
 */
public enum ImageDescriptorCache {

	/** The shared singleton cache for {@link ImageDescriptor image descriptor}s. */
	INSTANCE;

	private static final String PLUGIN_ID = N4JSActivator.getInstance().getBundle().getSymbolicName();
	private static final String ICON_FOLDER = "icons";

	/**
	 * Enumeration of image references. Basically the enumeration holds a reference to the {@link Image} or
	 * {@link ImageDescriptor}. If the image descriptor not cached by the {@link ImageDescriptorCache cache} then it
	 * caches it.
	 */
	public static enum ImageRef {

		/** The decorator for {@link ProjectType#TEST test} projects. */
		PROJECT_TYPE_TEST_DECOR("test_project_type.png"),

		/** The decorator for {@link ProjectType#APPLICATION application} projects. */
		PROJECT_TYPE_APP_DECOR("app_project_type.png"),

		/** The decorator for {@link ProjectType#LIBRARY library} projects. */
		PROJECT_TYPE_LIB_DECOR("lib_project_type.png"),

		/** The decorator for {@link ProjectType#PROCESSOR processor} projects. */
		PROJECT_TYPE_PROC_DECOR("proc_project_type.png"),

		/** The decorator for {@link ProjectType#RUNTIME_ENVIRONMENT runtime environment} projects. */
		PROJECT_TYPE_RE_DECOR("re_project_type.png"),

		/** The decorator for {@link ProjectType#RUNTIME_LIBRARY runtime library} projects. */
		PROJECT_TYPE_RL_DECOR("rl_project_type.png"),

		/** The decorator for {@link ProjectType#SYSTEM system} projects. */
		PROJECT_TYPE_SYS_DECOR("sys_project_type.png"),

		/** Library path image reference. */
		LIB_PATH("classpath.gif"),

		/** External library path image reference. */
		EXTERNAL_LIB_PATH("native_lib_path_attrib.gif"),

		/** Source folder image reference. */
		SRC_FOLDER("packagefolder_obj.gif"),

		/** Image reference for projects that are treated as either external or built-in libraries. */
		EXTERNAL_LIB_PROJECT("jar_l_obj.gif"),

		/** Wizard banner for the new N4JS project wizard. */
		NEW_PROJECT_WIZBAN("newprj_wiz.png"),

		/** Wizard banner for the new N4JS class wizard. */
		NEW_CLASS_WIZBAN("newclass_wiz.png"),

		/** Wizard banner for the new N4JS interface wizard. */
		NEW_INTERFACE_WIZBAN("newint_wiz.png"),

		/** Wizard banner for the new N4JS enum wizard. */
		NEW_ENUM_WIZBAN("newenum_wiz.png");

		private static final Logger LOGGER = Logger.getLogger(ImageRef.class);

		private final String fileName;

		private ImageRef(final String fileName) {
			this.fileName = fileName;
		}

		/**
		 * Returns with the cached image descriptor for the given reference. May return with {@link Optional#absent()
		 * absent} if the resource does not exist in the plug-in's {@code icons} folder. Or the plug-in is not running.
		 *
		 * @return the image descriptor for the image reference. Could be absent but never {@code null}.
		 */
		public Optional<ImageDescriptor> asImageDescriptor() {
			return ImageDescriptorCache.INSTANCE.getImageDescriptor(this);
		}

		/***
		 * Returns with a new image instance created from the cached image descriptor. May return with
		 * {@link Optional#absent() absent}, if the image descriptor cannot be created due to missing resource, or the
		 * image cannot be created from the image descriptor instance.
		 *
		 * @return the new image instance wrapped into an {@link Optional}. Can be {@link Optional#absent() missing} if
		 *         the image reference cannot be created.
		 */
		public Optional<Image> asImage() {
			try {
				return ImageDescriptorCache.IMAGE_CACHE.get(asImageDescriptor());
			} catch (final ExecutionException e) {
				LOGGER.error("Error while trying to get image from image descriptor of: " + this);
				return Optional.absent();
			}
		}

	}

	private synchronized Optional<ImageDescriptor> getImageDescriptor(final ImageRef ref) {
		final N4JSActivator activator = N4JSActivator.getInstance();
		if (null == activator) {
			return absent();
		}
		final ImageRegistry registry = activator.getImageRegistry();
		if (null == registry) {
			return absent();
		}
		ImageDescriptor descriptor = registry.getDescriptor(ref.fileName);
		if (null == descriptor) {
			final String imageFilePath = ICON_FOLDER + separator + ref.fileName;
			descriptor = imageDescriptorFromPlugin(PLUGIN_ID, imageFilePath);
			registry.put(ref.fileName, descriptor);
		}
		return Optional.fromNullable(descriptor);
	}

	private static final LoadingCache<Optional<ImageDescriptor>, Optional<Image>> IMAGE_CACHE = CacheBuilder
			.newBuilder().build(new CacheLoader<Optional<ImageDescriptor>, Optional<Image>>() {

				@Override
				public Optional<Image> load(Optional<ImageDescriptor> key) throws Exception {
					if (null == key || !key.isPresent()) {
						return Optional.fromNullable(ImageDescriptor.getMissingImageDescriptor().createImage());
					}
					return Optional.fromNullable(key.get().createImage());
				}

			});

}
