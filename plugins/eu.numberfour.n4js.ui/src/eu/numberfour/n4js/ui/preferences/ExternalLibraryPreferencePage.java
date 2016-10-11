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
package eu.numberfour.n4js.ui.preferences;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.primitives.Ints.asList;
import static eu.numberfour.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER;
import static eu.numberfour.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARY_NAMES;
import static eu.numberfour.n4js.external.libraries.TargetPlatformModel.TP_FILTER_EXTENSION;
import static eu.numberfour.n4js.n4mf.ProjectType.API;
import static eu.numberfour.n4js.ui.utils.UIUtils.getDisplay;
import static java.util.Collections.singletonList;
import static org.eclipse.jface.dialogs.MessageDialog.openError;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.jface.viewers.StyledString.DECORATIONS_STYLER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.END;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.OPEN;
import static org.eclipse.swt.SWT.PUSH;
import static org.eclipse.swt.SWT.SAVE;
import static org.eclipse.swt.SWT.Selection;
import static org.eclipse.swt.SWT.TOP;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.numberfour.n4js.binaries.IllegalBinaryStateException;
import eu.numberfour.n4js.external.ExternalLibrariesReloadHelper;
import eu.numberfour.n4js.external.ExternalLibraryWorkspace;
import eu.numberfour.n4js.external.NpmManager;
import eu.numberfour.n4js.external.TargetPlatformInstallLocationProvider;
import eu.numberfour.n4js.external.libraries.TargetPlatformModel;
import eu.numberfour.n4js.n4mf.ProjectType;
import eu.numberfour.n4js.preferences.ExternalLibraryPreferenceStore;
import eu.numberfour.n4js.projectModel.IN4JSProject;
import eu.numberfour.n4js.ui.ImageDescriptorCache.ImageRef;
import eu.numberfour.n4js.ui.binaries.IllegalBinaryStateDialog;
import eu.numberfour.n4js.ui.internal.N4jsActivator;
import eu.numberfour.n4js.ui.utils.UIUtils;
import eu.numberfour.n4js.ui.viewer.TreeViewerBuilder;
import eu.numberfour.n4js.utils.Arrays2;

/**
 * Preference page for managing external libraries.
 */
public class ExternalLibraryPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The unique preference page ID.
	 */
	public static final String ID = ExternalLibraryPreferencePage.class.getName();

	private static final Map<URI, String> BUILT_IN_LIBS = EXTERNAL_LIBRARIES_SUPPLIER.get();

	@Inject
	private ExternalLibraryPreferenceStore store;

	@Inject
	private Provider<ExternalLibraryTreeContentProvider> contentProvider;

	@Inject
	private NpmManager npmManager;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private ExternalLibrariesReloadHelper externalLibrariesReloadHelper;

	private TreeViewer viewer;

	@Override
	public void init(final IWorkbench workbench) {
		// Nothing.
	}

	@Override
	protected Control createContents(final Composite parent) {

		final Composite control = new Composite(parent, NONE);
		control.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());
		control.setLayoutData(fillDefaults().align(FILL, FILL).create());

		viewer = new TreeViewerBuilder(singletonList(""), contentProvider.get())
				.setVirtual(true)
				.setHeaderVisible(false)
				.setHasBorder(true)
				.setColumnWeights(asList(1))
				.setLabelProvider(new DelegatingStyledCellLabelProvider(new BuiltInLibrariesLabelProvider()))
				.build(control);

		setViewerInput();

		final Composite subComposite = new Composite(control, NONE);
		subComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		subComposite.setLayoutData(fillDefaults().align(END, TOP).create());

		createButton(subComposite, "Add...", new AddButtonSelectionListener());
		final Button remove = createButton(subComposite, "Remove", new RemoveButtonSelectionListener());
		remove.setEnabled(false);

		createPlaceHolderLabel(subComposite);

		final Button moveUp = createButton(subComposite, "Up", new MoveUpButtonSelectionListener());
		final Button moveDown = createButton(subComposite, "Down", new MoveDownButtonSelectionListener());
		moveUp.setEnabled(false);
		moveDown.setEnabled(false);

		createPlaceHolderLabel(subComposite);

		createButton(subComposite, "Reload", new ReloadButtonListener());

		createPlaceHolderLabel(subComposite);

		createButton(subComposite, "Install npm...", new InstallNpmDependencyButtonListener());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final /* @Nullable */ SelectionChangedEvent event) {
				final Tree tree = viewer.getTree();
				final TreeItem[] selection = tree.getSelection();
				if (!Arrays2.isEmpty(selection) && 1 == selection.length && selection[0].getData() instanceof URI) {
					final URI uri = (URI) selection[0].getData();
					if (BUILT_IN_LIBS.containsKey(uri)) {
						remove.setEnabled(false);
						moveUp.setEnabled(false);
						moveDown.setEnabled(false);
					} else {
						final int selectionIndex = tree.indexOf(selection[0]);
						final int itemCount = tree.getItemCount();
						remove.setEnabled(true);
						if (selectionIndex > 0) {
							moveUp.setEnabled(!BUILT_IN_LIBS.containsKey(tree.getItem(selectionIndex - 1).getData()));
						} else {
							moveUp.setEnabled(0 != selectionIndex);
						}
						moveDown.setEnabled(selectionIndex != itemCount - 1);
					}
				} else {
					remove.setEnabled(false);
					moveUp.setEnabled(false);
					moveDown.setEnabled(false);
				}
			}
		});

		return control;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		final Composite buttonParent = getApplyButton().getParent();
		final Layout layout = buttonParent.getLayout();
		if (layout instanceof GridLayout) {
			((GridLayout) layout).numColumns = ((GridLayout) layout).numColumns + 1;
		}
		createButton(buttonParent, "Export target platform...", new ExportButtonListener());
	}

	@Override
	protected void performApply() {
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, monitor -> {
				final IStatus status = store.save(monitor);
				if (!status.isOK()) {
					setMessage(status.getMessage(), ERROR);
				} else {
					updateInput(viewer, store.getLocations());
				}
			});
		} catch (final InvocationTargetException | InterruptedException exc) {
			throw new RuntimeException("Error while building external libraries.", exc);
		}
	}

	@Override
	protected void performDefaults() {
		store.resetDefaults();
		setViewerInput();
	}

	@Override
	public boolean performCancel() {
		store.invalidate();
		return true;
	}

	@Override
	public boolean performOk() {
		store.invalidate();
		return super.performOk();
	}

	private Button createButton(final Composite parent, final String text, final SelectionListener listener) {
		final Button button = new Button(parent, PUSH);
		button.setLayoutData(fillDefaults().align(FILL, CENTER).create());
		button.setText(text);
		if (null != listener) {
			button.addSelectionListener(listener);
			button.addDisposeListener(e -> {
				button.removeSelectionListener(listener);
			});
		}
		return button;
	}

	/**
	 * Asynchronously sets the the viewer input with the locations available from the
	 * {@link ExternalLibraryPreferenceStore store}.
	 */
	private void setViewerInput() {
		if (null != viewer && null != viewer.getControl() && !viewer.getControl().isDisposed()) {
			UIUtils.getDisplay().asyncExec(() -> updateInput(viewer, store.getLocations()));
		}
	}

	private Control createPlaceHolderLabel(final Composite parent) {
		return new Label(parent, NONE);
	}

	/**
	 * Simple label provider for external library locations.
	 */
	private static class BuiltInLibrariesLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private static final String BUILT_IN_SUFFIX = " [Built-in]";

		@Override
		public String getText(final Object element) {
			if (element instanceof URI) {
				final String externalLibId = BUILT_IN_LIBS.get(element);
				if (!isNullOrEmpty(externalLibId)) {
					return EXTERNAL_LIBRARY_NAMES.get(externalLibId) + BUILT_IN_SUFFIX;
				}
				return new File((URI) element).getAbsolutePath();
			} else if (element instanceof IN4JSProject) {
				return ((IN4JSProject) element).getProjectId();
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof URI) {
				return ImageRef.LIB_PATH.asImage().orNull();
			} else if (element instanceof IN4JSProject) {
				return ImageRef.EXTERNAL_LIB_PROJECT.asImage().orNull();
			}
			return super.getImage(element);
		}

		@Override
		public StyledString getStyledText(final Object element) {
			StyledString string = new StyledString(nullToEmpty(getText(element)));
			if (element instanceof URI) {
				final String text = string.getString();
				if (text.endsWith(BUILT_IN_SUFFIX)) {
					string.setStyle(text.lastIndexOf(BUILT_IN_SUFFIX), BUILT_IN_SUFFIX.length(), DECORATIONS_STYLER);
				}
			} else if (element instanceof IN4JSProject) {
				final ProjectType type = ((IN4JSProject) element).getProjectType();
				final String typeLabel = getProjectTypeLabel(type);
				string = new StyledString(string.getString() + typeLabel);
				string.setStyle(string.getString().lastIndexOf(typeLabel), typeLabel.length(), DECORATIONS_STYLER);
			}
			return string;
		}

		private String getProjectTypeLabel(final ProjectType projectType) {
			final String label;
			if (API.equals(projectType)) {
				label = API.getName();
			} else {
				label = toFirstUpper(nullToEmpty(projectType.getName()).replaceAll("_", " ").toLowerCase());
			}
			return " [" + label + "]";
		}

	}

	/**
	 * Selection listener for exporting the target platform file from the UI.
	 */
	private class ExportButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent ignored) {
			final FileDialog dialog = new FileDialog(getShell(), SAVE);
			dialog.setFilterExtensions(new String[] { TP_FILTER_EXTENSION });
			dialog.setFileName(TargetPlatformModel.TP_FILE_NAME);
			dialog.setText("Export N4 Target Platform");
			dialog.setOverwrite(true);
			final String value = dialog.open();
			if (!isNullOrEmpty(value)) {
				final URI location = installLocationProvider.getTargetPlatformNodeModulesLocation();
				final Iterable<IProject> projects = externalLibraryWorkspace.getProjects(location);
				final Iterable<String> projectIds = from(projects).transform(p -> p.getName());
				final TargetPlatformModel model = TargetPlatformModel.createFromNpmProjectIds(projectIds);
				final File file = new File(value);
				try {
					if (!file.exists()) {
						checkState(file.createNewFile(), "Error while exporting target platform file.");
					}
					try (final PrintWriter pw = new PrintWriter(file)) {
						pw.write(model.toString());
						pw.flush();
					}
				} catch (final IOException e) {
					throw new RuntimeException("Error while exporting target platform file.", e);
				}
			}
		}

	}

	/**
	 * Button selection listener for opening up an {@link InputDialog input dialog}, where user can specify npm package
	 * name that will be downloaded and installed to the external libraries.
	 *
	 * Note: this class is not static, so it will hold reference to all services. Make sure to dispose it.
	 *
	 */
	private class InstallNpmDependencyButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final Collection<String> installedNpmPackageNames = getInstalledNpmPackages();
			final InputDialog dialog = new InputDialog(UIUtils.getShell(), "npm Install",
					"Specify an npm package name to download and install:", null, new IInputValidator() {

						@Override
						public String isValid(final String newText) {

							if (StringExtensions.isNullOrEmpty(newText)) {
								return "The npm package name should be specified.";
							}

							for (int i = 0; i < newText.length(); i++) {
								if (Character.isWhitespace(newText.charAt(i))) {
									return "The npm package name must not contain any whitespaces.";
								}
							}

							for (int i = 0; i < newText.length(); i++) {
								if (Character.isUpperCase(newText.charAt(i))) {
									return "The npm package name must not contain any upper case letter.";
								}
							}

							if (installedNpmPackageNames.contains(newText)) {
								return "The npm package '" + newText + "' is already available.";
							}

							return null;
						}
					});

			dialog.open();
			final String packageName = dialog.getValue();
			if (!StringExtensions.isNullOrEmpty(packageName)) {
				final AtomicReference<IStatus> errorStatusRef = new AtomicReference<>();
				final AtomicReference<IllegalBinaryStateException> illegalBinaryExcRef = new AtomicReference<>();
				try {
					new ProgressMonitorDialog(UIUtils.getShell()).run(true, false, monitor -> {
						try {
							IStatus status = npmManager.installDependency(packageName, monitor);
							if (status.isOK()) {
								updateInput(viewer, store.getLocations());
							} else {
								// Raise the error dialog just when this is closed.
								errorStatusRef.set(status);
							}
						} catch (final IllegalBinaryStateException ibse) {
							illegalBinaryExcRef.set(ibse);
						}
					});
				} catch (final InvocationTargetException | InterruptedException exc) {
					throw new RuntimeException("Error while installing npm dependency: '" + packageName + "'.", exc);
				} finally {
					if (null != illegalBinaryExcRef.get()) {
						new IllegalBinaryStateDialog(illegalBinaryExcRef.get()).open();
					} else if (null != errorStatusRef.get()) {
						N4jsActivator.getInstance().getLog().log(errorStatusRef.get());
						getDisplay().asyncExec(() -> openError(
								getShell(),
								"npm Install Failed",
								"Error while installing '" + packageName
										+ "' npm package.\nPlease check your Error Log view for the detailed npm log about the failure."));
					}
				}
			}
		}

		private Collection<String> getInstalledNpmPackages() {
			final File root = new File(installLocationProvider.getTargetPlatformNodeModulesLocation());
			return from(externalLibraryWorkspace.getProjects(root.toURI())).transform(p -> p.getName()).toSet();
		}

	}

	/**
	 * Listener that refreshes any definition files from the local git repository and reloads the external libraries in
	 * blocking fashion.
	 */
	private class ReloadButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {

			try {
				new MutableProgressMonitorDialog(getShell()).run(true, true, monitor -> {
					externalLibrariesReloadHelper.reloadLibraries(true, monitor);
				});
			} catch (final InvocationTargetException | InterruptedException exc) {
				throw new RuntimeException("Error while re-building external libraries.", exc);
			}
		}

	}

	/**
	 * Selection listener for adding a new external library location.
	 */
	private class AddButtonSelectionListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final String directoryPath = new DirectoryDialog(viewer.getControl().getShell(), OPEN).open();
			if (null != directoryPath) {
				final File file = new File(directoryPath);
				if (file.exists() && file.isDirectory()) {
					store.add(file.toURI());
					updateInput(viewer, store.getLocations());
				}
			}
		}
	}

	/**
	 * Selection listener for adding a new external library location.
	 */
	private class RemoveButtonSelectionListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final ISelection selection = viewer.getSelection();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				final Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof URI) {
					store.remove((URI) element);
					updateInput(viewer, store.getLocations());
				}
			}
		}
	}

	/**
	 * Selection listener for moving up an external library location in the list.
	 */
	private class MoveUpButtonSelectionListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final ISelection selection = viewer.getSelection();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				final Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof URI) {
					store.moveUp((URI) element);
					updateInput(viewer, store.getLocations());
				}
			}
		}

	}

	/**
	 * Selection listener for moving down an external library location in the list.
	 */
	private class MoveDownButtonSelectionListener extends SelectionAdapter {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final ISelection selection = viewer.getSelection();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				final Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof URI) {
					store.moveDown((URI) element);
					updateInput(viewer, store.getLocations());
				}
			}
		}

	}

	private static void updateInput(final TreeViewer viewer, final Object input) {
		UIUtils.getDisplay().asyncExec(() -> {
			final Object[] expandedElements = viewer.getExpandedElements();
			final TreePath[] expandedTreePaths = viewer.getExpandedTreePaths();
			viewer.setInput(input);
			viewer.getControl().notifyListeners(Selection, null);
			if (!Arrays2.isEmpty(expandedElements)) {
				viewer.setExpandedElements(expandedElements);
			}
			if (!Arrays2.isEmpty(expandedTreePaths)) {
				viewer.setExpandedTreePaths(expandedTreePaths);
			}
		});
	}

}
