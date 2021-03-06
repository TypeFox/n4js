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
package eu.numberfour.n4js.tests.realworld;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.valueOf;
import static java.text.MessageFormat.format;
import static org.eclipse.core.resources.IMarker.SEVERITY;
import static org.eclipse.core.resources.IMarker.SEVERITY_WARNING;
import static org.eclipse.xtext.validation.Issue.CODE_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil;
import org.eclipse.xtext.ui.MarkerTypes;
import org.junit.Test;

import eu.numberfour.n4js.tests.builder.AbstractBuilderParticipantTest;
import eu.numberfour.n4js.tests.util.ProjectUtils;
import eu.numberfour.n4js.validation.IssueCodes;

/**
 * Tests parsing and validation of ListBase and underscore.
 */
@SuppressWarnings("restriction")
public class ListBasePluginTest extends AbstractBuilderParticipantTest {

	/**
	 * The number of the expected validation markers are increased from 0 to 21 due to introducing the Constraints 2
	 * (N4JS identifier recommendations) in the specification. In a nutshell: & (dollar sign) is discouraged to be used
	 * in field and variable names. See issue IDE-1143 for more details.
	 */
	private static final int NUMBER_OF_EXPECTED_ISSUES = 25;

	private static final String EXPECTED_NUMBER_OF_ISSUE_TEMPLATE = //
			"Expected exactly " + NUMBER_OF_EXPECTED_ISSUES + " validation issues but found {0} instead.";

	private static final int UNKNOWN_SEVERITY = -1;

	/**
	 * Predicate to find all expected validations issues due to variables/fields violating the Constraints 2 (N4JS
	 * identifier recommendations) constraint from the specification.
	 */
	private static final Predicate<IMarker> EXPECTED_VALIDATION_PREDICATE = //
			new Predicate<IMarker>() {

				private final List<String> EXPECTED_ERROR_CODES = Arrays.asList(
						IssueCodes.AST_LOCAL_VAR_UNUSED,
						IssueCodes.CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER);

				@Override
				public boolean test(final IMarker marker) {
					return hasWarningSeverity(marker) && hasExpectedErrorCode(marker);
				}

				private boolean hasWarningSeverity(final IMarker marker) {
					return SEVERITY_WARNING == marker.getAttribute(SEVERITY, UNKNOWN_SEVERITY);
				}

				private boolean hasExpectedErrorCode(final IMarker marker) {
					try {
						return EXPECTED_ERROR_CODES.contains(valueOf(marker.getAttribute(CODE_KEY)));
					} catch (final CoreException e) {
						throw new RuntimeException("Error while getting the error code for marker: " + marker, e);
					}
				}

			};

	@SuppressWarnings("javadoc")
	@Test
	public void testListBase() throws Exception {
		IProject project = ProjectUtils.importProject(new File("probands"), "ListBase");
		IResourcesSetupUtil.waitForBuild();
		IFile underscore_js = project.getFile("src/underscore/underscore.n4js");
		assertTrue(underscore_js.exists());
		assertMarkers(underscore_js + " should have no errors, one unused variable warning", underscore_js, 1);

		IFile listbase_js = project.getFile("src/n4/lang/ListBase.n4js");

		assertTrue(listbase_js.exists());

		final Collection<IMarker> allMarkers = newHashSet(listbase_js.findMarkers(MarkerTypes.ANY_VALIDATION, true,
				IResource.DEPTH_INFINITE));
		assertTrue(format(EXPECTED_NUMBER_OF_ISSUE_TEMPLATE, allMarkers.size()),
				NUMBER_OF_EXPECTED_ISSUES == allMarkers.size());

		long unexpectedMarkerCount = allMarkers.stream()
				.filter(EXPECTED_VALIDATION_PREDICATE.negate())
				.count();

		assertTrue("Unexpected validation issues were found. " + toString(allMarkers), unexpectedMarkerCount == 0);
	}

	private String toString(final Iterable<IMarker> markers) {
		StringBuilder sb = new StringBuilder();
		if (markers != null) {
			for (IMarker marker : markers) {
				try {
					sb.append(marker.getAttribute("message")).append(" at line ")
							.append(marker.getAttribute(IMarker.LINE_NUMBER)).append("\n");
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
