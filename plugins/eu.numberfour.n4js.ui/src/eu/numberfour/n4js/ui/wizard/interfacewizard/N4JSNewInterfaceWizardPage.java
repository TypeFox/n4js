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
package eu.numberfour.n4js.ui.wizard.interfacewizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;

import eu.numberfour.n4js.N4JSGlobals;
import eu.numberfour.n4js.ui.dialog.ModuleSpecifierSelectionDialog;
import eu.numberfour.n4js.ui.wizard.components.AccessModifierComponent;
import eu.numberfour.n4js.ui.wizard.components.DefinitionFileComponent;
import eu.numberfour.n4js.ui.wizard.components.EmptyComponent;
import eu.numberfour.n4js.ui.wizard.components.InterfacesComponentProvider;
import eu.numberfour.n4js.ui.wizard.components.NameComponent;
import eu.numberfour.n4js.ui.wizard.components.OtherInterfaceModifiersComponent;
import eu.numberfour.n4js.ui.wizard.components.WizardComponentContainer;
import eu.numberfour.n4js.ui.wizard.components.WizardComponentDataConverters;
import eu.numberfour.n4js.ui.wizard.model.AccessModifiableModel;
import eu.numberfour.n4js.ui.wizard.model.AccessModifiableModel.AccessModifier;
import eu.numberfour.n4js.ui.wizard.model.DefinitionFileModel;
import eu.numberfour.n4js.ui.wizard.model.NamedModel;
import eu.numberfour.n4js.ui.wizard.workspacewizard.SuffixText;
import eu.numberfour.n4js.ui.wizard.workspacewizard.WorkspaceWizardModel;
import eu.numberfour.n4js.ui.wizard.workspacewizard.WorkspaceWizardModelValidator;
import eu.numberfour.n4js.ui.wizard.workspacewizard.WorkspaceWizardModelValidator.ValidationResult;
import eu.numberfour.n4js.ui.wizard.workspacewizard.WorkspaceWizardPage;

/**
 * A wizard page to allow the user to specify the informations about the creation of a new interface.
 *
 * <p>
 * Note: The wizard page is not meant to be used without setting a {@link N4JSInterfaceWizardModel}. To accomplish this
 * use the {@link N4JSNewInterfaceWizardPage#setModel(N4JSInterfaceWizardModel)} method.
 * </p>
 *
 */
public class N4JSNewInterfaceWizardPage extends WorkspaceWizardPage {

	N4JSInterfaceWizardModel model = null;

	@Inject
	private N4JSInterfaceWizardModelValidator validator;

	// Components
	@Inject
	private InterfacesComponentProvider interfacesComponentProvider;

	private OtherInterfaceModifiersComponent otherInterfaceModifiersComponent;
	private NameComponent nameComponent;

	private AccessModifierComponent accessModifierComponent;

	/**
	 * Instantiates a New N4JS Interface wizard main page
	 */
	public N4JSNewInterfaceWizardPage() {
		super("Create a new N4JS Interface");

		this.setTitle("New N4JS Interface");
		this.setMessage("Create a new N4JS Interface");
		this.setPageComplete(false);
	}

	/**
	 * Set the model to use with this wizard page.
	 *
	 * @param model
	 *            N4JSInterfaceWizardModel to use
	 */
	public void setModel(N4JSInterfaceWizardModel model) {
		super.setModel(model);

		this.model = model;
		this.validator.setModel(this.model);
		this.model.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				validator.validate();
			}
		});
	}

	@Override
	public void setModel(WorkspaceWizardModel model) {
		throw new UnsupportedOperationException("The wizard page can only be used with N4JSInterfaceWizardModel");
	}

	/**
	 * Invoked when the validation result of the model has changed.
	 *
	 * @param result
	 *            The most recent validation result
	 */
	private void onValidationChange(ValidationResult result) {
		if (result.valid) {
			this.setPageComplete(true);
			this.setMessage("Press finish to create the new interface");
			this.setErrorMessage(null);
		} else {
			this.setPageComplete(false);
			this.setErrorMessage(result.errorMessage);
		}
	}

	@Override
	public void openModuleSpecifierDialog(org.eclipse.swt.widgets.Shell shell) {

		ModuleSpecifierSelectionDialog dialog = new ModuleSpecifierSelectionDialog(shell,
				model.getProject().append(model.getSourceFolder()));

		if (!model.getEffectiveModuleSpecifier().isEmpty()) {
			String initialSelectionSpecifier = model.getEffectiveModuleSpecifier();

			String fileExtension = model.computeFileLocation().getFileExtension();
			if (fileExtension != null) {
				dialog.setDefaultFileExtension(fileExtension);
			}

			Object initialSelection = dialog.computeInitialSelection(initialSelectionSpecifier);
			dialog.setInitialSelection(initialSelection);
		}

		dialog.open();

		Object result = dialog.getFirstResult();

		if (result instanceof String) {

			IPath specifierPath = new Path((String) result);

			String fileExtension = specifierPath.getFileExtension();

			// If the selected module specifier is a file
			if (fileExtension != null && !fileExtension.isEmpty()) {
				// and its file extension suggests a different external value than the model
				if (fileExtension.equals(N4JSGlobals.N4JSD_FILE_EXTENSION) != model.isDefinitionFile()) {
					// toggle the external value of the model
					model.setDefinitionFile(!model.isDefinitionFile());
				}
				specifierPath = specifierPath.removeFileExtension();
			}

			// If the last segment corresponds with the non-empty interface name remove it
			if (specifierPath.segmentCount() > 0
					&& specifierPath.removeFileExtension().lastSegment().equals(model.getName())
					&& !model.getName().isEmpty()) {
				if (specifierPath.segmentCount() > 1) {
					specifierPath = specifierPath.removeLastSegments(1).addTrailingSeparator();
				} else {
					specifierPath = specifierPath.removeLastSegments(1);
				}
			}

			model.setModuleSpecifier(specifierPath.toString());
		}
	}

	/**
	 * Setup additional non-component contained bindings
	 */
	private void setupBindings() {

		DataBindingContext dataBindingContext = this.getDataBindingContext();

		IObservableValue moduleSpecifierValue = BeanProperties
				.value(WorkspaceWizardModel.class, WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY).observe(model);

		//// Only show the suffix on input values ending with a '/' character or empty module specifiers.
		moduleSpecifierValue.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				SuffixText input = workspaceWizardForm.getModuleSpecifierText();
				String inputText = input.getText();
				if (inputText.isEmpty() || inputText.charAt(inputText.length() - 1) == '/') {
					input.setSuffixVisible(true);
				} else {
					input.setSuffixVisible(false);
				}
			}
		});

		//// interface name to module specifier suffix binding
		IObservableValue interfaceNameModelValue = BeanProperties
				.value(N4JSInterfaceWizardModel.class, NamedModel.NAME_PROPERTY).observe(model);
		IObservableValue greySuffixValue = BeanProperties.value(SuffixText.class, SuffixText.SUFFIX_PROPERTY)
				.observe(workspaceWizardForm.getModuleSpecifierText());
		dataBindingContext.bindValue(greySuffixValue,
				interfaceNameModelValue, noUpdateValueStrategy(),
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE));

		//// Enable n4js <-> Definition value(external) is selected
		IObservableValue externalValue = BeanProperties
				.value(DefinitionFileModel.class, DefinitionFileModel.DEFINITION_FILE_PROPERTY)
				.observe(model);

		IObservableValue n4jsEnabled = WidgetProperties.enabled()
				.observe(otherInterfaceModifiersComponent.getN4jsAnnotationBox());
		dataBindingContext.bindValue(n4jsEnabled, externalValue);

		// One way binding of the access modifiers to the enabled state of internal checkbox
		IObservableValue internalEnabledValue = WidgetProperties.enabled()
				.observe(accessModifierComponent.getInternalAnnotationBox());
		IObservableValue accessModifierSelectObservable = BeanProperties
				.value(N4JSInterfaceWizardModel.class, AccessModifiableModel.ACCESS_MODIFIER_PROPERTY).observe(model);

		dataBindingContext.bindValue(internalEnabledValue, accessModifierSelectObservable, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(object -> {
					if (object instanceof AccessModifier) {
						if (((AccessModifier) object) == AccessModifier.PUBLIC) {
							return true;
						}
					}
					return false;
				}));

		// N4JS annotation checkbox disabled when access modifier is private
		IObservableValue n4jsEnabledValue = WidgetProperties.enabled()
				.observe(otherInterfaceModifiersComponent.getN4jsAnnotationBox());
		dataBindingContext.bindValue(n4jsEnabledValue, accessModifierSelectObservable, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(object -> {
					if (object instanceof AccessModifier) {
						return ((AccessModifier) object != AccessModifier.PRIVATE);
					}
					return false;
				}));

		// Refresh wizard state on validation change
		IObservableValue observableValidationValue = BeanProperties
				.value(WorkspaceWizardModelValidator.VALIDATION_RESULT)
				.observe(this.validator);
		observableValidationValue.addValueChangeListener(new IValueChangeListener() {

			@Override
			public void handleValueChange(ValueChangeEvent event) {
				onValidationChange((ValidationResult) event.diff.getNewValue());
			}

		});

	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		// Set initial ui state
		otherInterfaceModifiersComponent.getN4jsAnnotationBox().setEnabled(model.isDefinitionFile());
	}

	@Override
	protected boolean setInitialFocus() {
		if (!super.setInitialFocus()) {
			this.nameComponent.setFocus();
		}
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public void createComponents(WizardComponentContainer container) {

		// instance variable to set intial focus
		nameComponent = new NameComponent(model, container);

		new EmptyComponent(container);

		new DefinitionFileComponent(model, container);

		accessModifierComponent = new AccessModifierComponent(model, container);

		// instance variable to allow custom bindings
		otherInterfaceModifiersComponent = new OtherInterfaceModifiersComponent(model, container);

		new EmptyComponent(container);

		interfacesComponentProvider.create(model, container);

		setupBindings();
	}

	@Override
	public N4JSInterfaceWizardModelValidator getValidator() {
		return this.validator;
	}

	private static UpdateValueStrategy noUpdateValueStrategy() {
		return new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER);
	}

}
