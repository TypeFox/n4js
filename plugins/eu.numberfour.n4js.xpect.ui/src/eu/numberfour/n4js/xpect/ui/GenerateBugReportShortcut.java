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
package eu.numberfour.n4js.xpect.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import eu.numberfour.n4js.xpect.ui.results.XpectBugReportUtil;

/**
 * Launch shortcut for generating bug report in a console.
 */
public class GenerateBugReportShortcut implements ILaunchShortcut {

	/***/
	public GenerateBugReportShortcut() {
	}

	private static void showDialogNotImplemented(String what) {
		MessageDialog.openWarning(null, "Warning", "Launching of type " + what + " is not implemeneted yet!");
	}

	@Override
	public void launch(ISelection selection, String mode) {
		Object selectObj = ((IStructuredSelection) selection).getFirstElement();
		if (selectObj instanceof IFile) {
			generateBug((IFile) selectObj);
		} else {
			showDialogNotImplemented(selection.getClass().getName());
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFile selectObj = ((IFileEditorInput) editorInput).getFile();
			generateBug(selectObj);
		} else {
			showDialogNotImplemented(editor.getClass().getName());
		}
	}

	/**
	 * Launch an file, using the file information, which means using default launch configurations.
	 */
	protected void generateBug(IFile fileSelectedToRun) {
		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(fileSelectedToRun.getRawLocationURI())));
		} catch (IOException e) {
			throw new RuntimeException("Cannot read profided file " + fileSelectedToRun.getName(), e);
		}

		System.out.println(content);
		XpectBugReportUtil.displayGeneratedBugConsole(fileSelectedToRun.getName(), content);
	}

}
