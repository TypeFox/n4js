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
package eu.numberfour.n4js.xpect;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static eu.numberfour.n4js.utils.io.FileUtils.createDirectory;
import static eu.numberfour.n4js.utils.io.FileUtils.getTempFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.xpect.xtext.lib.setup.FileSetupContext;
import org.xpect.xtext.lib.setup.emf.ResourceFactory;
import org.xpect.xtext.lib.setup.workspace.Workspace;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.numberfour.n4js.AnnotationDefinition;
import eu.numberfour.n4js.generator.common.AbstractSubGenerator;
import eu.numberfour.n4js.n4JS.Annotation;
import eu.numberfour.n4js.n4JS.AnnotationArgument;
import eu.numberfour.n4js.n4JS.Literal;
import eu.numberfour.n4js.n4JS.LiteralAnnotationArgument;
import eu.numberfour.n4js.n4JS.Script;
import eu.numberfour.n4js.n4JS.StringLiteral;
import eu.numberfour.n4js.projectModel.IN4JSCore;
import eu.numberfour.n4js.projectModel.IN4JSProject;
import eu.numberfour.n4js.projectModel.IN4JSSourceContainer;
import eu.numberfour.n4js.runner.IExecutor;
import eu.numberfour.n4js.runner.RunConfiguration;
import eu.numberfour.n4js.runner.RunnerFrontEnd;
import eu.numberfour.n4js.runner.SystemLoaderInfo;
import eu.numberfour.n4js.runner.extension.RunnerRegistry;
import eu.numberfour.n4js.runner.nodejs.NodeRunner;
import eu.numberfour.n4js.runner.nodejs.NodeRunner.NodeRunnerDescriptorProvider;
import eu.numberfour.n4js.transpiler.es.EcmaScriptSubGenerator;

/**
 * Xpect helper that allows to compile and execute resources on demand in xpect tests. Uses
 * {@link EcmaScriptSubGenerator} for compilation and {@link NodeRunner} for execution.
 */
public class XpectN4JSES5TranspilerHelper {

	@Inject
	private IN4JSCore core;

	@Inject
	private IResourceValidator resourceValidator;

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private RunnerRegistry runnerRegistry;

	/**
	 * Java value object to passed to ISetupInitializer to let extract the configuration via reflection. Xpect look for
	 * add-methods and checks its input parameter type whether there is a configuration of same type and then passes
	 * this object to this add-method.
	 */
	public static abstract class ReadOutConfiguration {

		/** Resource set initialized from {@link IN4JSCore} and wrapped into a delegate. */
		protected final ResourceSet resourceSet;
		/** The Xtext index for the resource set. */
		protected final IResourceDescriptions index;
		/** Context of the current running Xpect test. */
		protected final FileSetupContext fileSetupCtx;

		ReadOutConfiguration(final FileSetupContext ctx, final IN4JSCore core) {
			this.resourceSet = core.createResourceSet(absent());
			index = core.getXtextIndex(this.resourceSet);
			this.fileSetupCtx = new DuplicateResourceAwareFileSetupContext(ctx);
		}

		/**
		 * @return the resources retrieved from the Xpect resource set configuration
		 */
		public abstract List<Resource> getResources();
	}

	/**
	 * Reads out Xpect ResourceSet configuration to retrieve EMF resources from there.
	 */
	public static class ReadOutResourceSetConfiguration extends ReadOutConfiguration {
		private org.xpect.xtext.lib.setup.emf.ResourceSet configuredResourceSet;

		ReadOutResourceSetConfiguration(FileSetupContext ctx, IN4JSCore resourceSet) {
			super(ctx, resourceSet);
		}

		/**
		 * @param xpectResourceSet
		 *            the Xpect configuration item to be read out
		 */
		public void add(org.xpect.xtext.lib.setup.emf.ResourceSet xpectResourceSet) {
			this.configuredResourceSet = xpectResourceSet;
		}

		/**
		 * @return the resources retrieved from the Xpect resource set configuration
		 */
		@Override
		public List<Resource> getResources() {
			final List<Resource> configuredResources = newArrayList();
			if (configuredResourceSet != null) {
				for (ResourceFactory factory : configuredResourceSet.getFactories()) {
					if (factory instanceof org.xpect.xtext.lib.setup.emf.Resource) {
						org.xpect.xtext.lib.setup.emf.Resource res = (org.xpect.xtext.lib.setup.emf.Resource) factory;
						try {
							if (fileSetupCtx != null) {
								Resource createdRes = res.create(fileSetupCtx, resourceSet);
								configuredResources.add(createdRes);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return configuredResources;
		}
	}

	/**
	 * Reads out Xpect Workspace setup configuration to retrieve EMF resources from there.
	 */
	public static class ReadOutWorkspaceConfiguration extends ReadOutConfiguration {
		private Workspace configuredWorkspace;
		private final FileExtensionProvider fileExtensionProvider;

		ReadOutWorkspaceConfiguration(FileSetupContext ctx, IN4JSCore core,
				FileExtensionProvider fileExtensionProvider) {
			super(ctx, core);
			this.fileExtensionProvider = fileExtensionProvider;
		}

		/**
		 * @param workspace
		 *            the Xpect configuration item to be read out
		 */
		public void add(org.xpect.xtext.lib.setup.workspace.Workspace workspace) {
			this.configuredWorkspace = workspace;
		}

		/**
		 * @return the resources retrieved from the Xpect resource set configuration
		 */
		@Override
		public List<Resource> getResources() {
			final List<Resource> configuredResources = newArrayList();
			if (configuredWorkspace != null && fileSetupCtx != null) {
				for (IResourceDescription res : index.getAllResourceDescriptions()) {
					if (fileExtensionProvider.isValid(res.getURI().fileExtension())) {
						configuredResources.add(resourceSet.getResource(res.getURI(), true));
					}
				}
			}
			return configuredResources;
		}
	}

	private final String nl = "\n";

	/**
	 * Injection - method used to lazily initialize the runnerRegistry. Called after field-injection.
	 */
	@Inject
	private void init(NodeRunnerDescriptorProvider descriptorProvider) {
		if (!runnerRegistry.isRegistered(NodeRunner.ID)) {
			runnerRegistry.register(descriptorProvider.get());
		}
	}

	/**
	 * Compile provided resource, execute in Node.js and return execution output.
	 *
	 * @param resource
	 *            Script to execute
	 * @param init
	 *            xpect setup-init
	 * @param fileSetupContext
	 *            xpect injected
	 * @param decorateStdStreams
	 *            false-just connect stdout+stderr or errors to resulting string, true - decorate the streams with
	 *            "<== stdout: ..."
	 * @param resourceTweaker
	 *            - resource-modifier like QuickFix application, can be null
	 * @param systemLoader
	 *            the system loader to use (SYSTEM_JS[default], COMMON_JS,...)
	 * @return output streams concatenated
	 */
	public String doCompileAndExecute(final XtextResource resource, org.xpect.setup.ISetupInitializer<Object> init,
			FileSetupContext fileSetupContext, boolean decorateStdStreams, ResourceTweaker resourceTweaker,
			SystemLoaderInfo systemLoader)
			throws IOException {

		final Iterable<Resource> dependencies = from(getDependentResources(init, fileSetupContext));
		boolean replaceQuotes = false;

		// compile all file resources
		StringBuilder errorResult = new StringBuilder();

		// Apply some modification to the resource here.
		if (resourceTweaker != null) {
			resourceTweaker.tweak(resource);
		}

		// replace n4jsd resource with provided js resource
		for (final Resource dep : from(dependencies).filter(r -> !r.getURI().equals(resource.getURI()))) {
			if ("n4jsd".equalsIgnoreCase(dep.getURI().fileExtension())) {
				compileImplementationOfN4JSDFile(errorResult, dep, replaceQuotes);
			} else if (isCompilable(dep, errorResult)) {
				final Script script = (Script) dep.getContents().get(0);
				createTempJsFileWithScript(script, replaceQuotes); // IDE-2094 use a specific temp-folder here!
			}
		}

		// determine module to run
		Script script = (Script) resource.getContents().get(0);

		String executionResult = null;
		List<String> combinedOutput = new ArrayList<>();
		try {
			if (errorResult.length() == 0) {

				createTempJsFileWithScript(script, replaceQuotes); // IDE-2094 use a specific temp-folder here!

				String fileToRun = jsModulePathToRun(script);
				RunConfiguration runConfig = runnerFrontEnd.createXpectOutputTestConfiguration(NodeRunner.ID, fileToRun,
						systemLoader);

				Process process = runnerFrontEnd.run(runConfig, executor());
				EngineOutput eo = captureOutput(process);

				if (decorateStdStreams) {
					combinedOutput.add("<==");
					combinedOutput.add("stdout:");
				}
				combinedOutput.addAll(eo.getStdOut());
				if (decorateStdStreams) {
					combinedOutput.add("stderr:");
				}
				combinedOutput.addAll(eo.getErrOut());
				if (decorateStdStreams) {
					combinedOutput.add("==>");
				}
			} else {
				combinedOutput.add(errorResult.toString());
			}
			executionResult = Joiner.on(nl).join(combinedOutput);
		} catch (Exception e) {
			executionResult = e.getMessage();
			// TODO Debugging:
			System.out.println("Error in N4js execution " + e);
			e.printStackTrace();

		}
		return executionResult;
	}

	/**
	 * private Executor which is not redirecting out & err streams.
	 */
	private IExecutor executor() {
		return new IExecutor() {
			@Override
			public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> envp)
					throws ExecutionException {

				ProcessBuilder pb = new ProcessBuilder(cmdLine);
				pb.environment().putAll(envp);
				pb.directory(workingDirectory);

				try {
					return pb.start();
				} catch (IOException e) {
					throw new ExecutionException(e);
				}
			}
		};
	}

	/**
	 * Composes the name of the module as seen from the node-path. Is uses '/' as the path-separator, since it the path
	 * is consumed from js-code.
	 */
	private String jsModulePathToRun(Script script) {
		ArrayList<String> pathSegements = newArrayList(getProjectName(script));
		pathSegements.addAll(moduleQualifiedNameSegments(script));
		String fileToRun = Joiner.on('/').join(pathSegements);
		return fileToRun;
	}

	/**
	 * This operation is blocking (waits for process to finish).
	 *
	 * @param process
	 *            the process to get the standard out and error from.
	 *
	 * @return output captured during process run
	 */
	protected EngineOutput captureOutput(Process process) throws IOException {
		List<String> out = new ArrayList<>();
		List<String> err = new ArrayList<>();
		String outLine;
		String errLine;
		BufferedReader bri = null;
		BufferedReader bre = null;
		try {
			bri = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((outLine = bri.readLine()) != null) {
				out.add(outLine);
				// log.debug(outLine);
			}
			bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((errLine = bre.readLine()) != null) {
				err.add(errLine);
				System.err.println(errLine);
			}
		} catch (Exception e) {
			System.err.println("Exception in Engine.captureOutput: " + e.getMessage());
		} finally {
			if (bri != null) {
				bri.close();
			}
			if (bre != null) {
				bre.close();
			}
		}
		return new EngineOutput(out, err);
	}

	/**
	 * Compiles provided Script. Can replace backslashes with single quote (for windows command line issues)
	 *
	 * @param depRoot
	 *            script to transpile
	 * @param replaceQuotes
	 *            should replace quotes (only for windows)
	 * @return string representation of compilation result
	 */
	public String compile(Script depRoot, boolean replaceQuotes) {
		final AbstractSubGenerator generator = getGeneratorForResource(depRoot.eResource());
		String compileResultStr = generator.getCompileResultAsText(depRoot);
		if (replaceQuotes) {
			// Windows Node.js has problems with " as it interprets it as ending of script to execute
			compileResultStr = compileResultStr.replace("\"", "'");
		}
		return compileResultStr;
	}

	/**
	 * Checks if given resource can be compiled. During the process validators for that resource are executed.
	 *
	 * @param resource
	 *            {@link Resource} that will check
	 * @param errorResult
	 *            {@link StringBuilder} to which potential validation issues will be appended
	 * @return {@code true} if there are no validation issues for resource and it can be compiled
	 */
	public boolean isCompilable(Resource resource, StringBuilder errorResult) {
		final AbstractSubGenerator generator = getGeneratorForResource(resource);
		// shouldBeCompiled already calls the resource validator, so
		// registerErrors should only be called when there have been errors found before
		return generator.shouldBeCompiled(resource, CancelIndicator.NullImpl) || !registerErrors(resource, errorResult);
	}

	/**
	 * Returns the desired generator instance for the resource argument. For now, will always return an instance of
	 * {@link EcmaScriptSubGenerator}.
	 */
	private AbstractSubGenerator getGeneratorForResource(Resource resource) {
		checkState(resource instanceof XtextResource, "Expected XtextResource was " + resource);
		return ((XtextResource) resource).getResourceServiceProvider().get(EcmaScriptSubGenerator.class);
	}

	private List<Resource> getDependentResources(
			org.xpect.setup.ISetupInitializer<Object> init, FileSetupContext fileSetupContext) {

		List<Resource> resourcesFromXpectConfiguredResourceSet = new ArrayList<>();
		final ReadOutConfiguration readOutConfiguration;
		if (Platform.isRunning()) {
			readOutConfiguration = new ReadOutWorkspaceConfiguration(fileSetupContext, core, fileExtensionProvider);
		} else {
			readOutConfiguration = new ReadOutResourceSetConfiguration(fileSetupContext, core);
		}
		init.initialize(readOutConfiguration);
		resourcesFromXpectConfiguredResourceSet = readOutConfiguration.getResources();
		return Collections.unmodifiableList(new ArrayList<>(resourcesFromXpectConfiguredResourceSet));
	}

	private boolean registerErrors(Resource dep, StringBuilder errorResult) {
		boolean hasErrors = false;
		List<Issue> issues = resourceValidator.validate(dep, CheckMode.ALL, CancelIndicator.NullImpl);
		List<Issue> errorIssues = new ArrayList<>();
		for (Issue issue : issues) {
			if (Severity.ERROR == issue.getSeverity()) {
				errorIssues.add(issue);
			}
		}
		hasErrors = !errorIssues.isEmpty();
		if (hasErrors) {
			errorResult.append("Couldn't compile resource " + dep.getURI() + " because it contains errors: ");
			for (Issue errorIssue : errorIssues) {
				errorResult
						.append(nl + errorIssue.getMessage() + " at line " + errorIssue.getLineNumber());
			}
		}
		return hasErrors;
	}

	private void compileImplementationOfN4JSDFile(StringBuilder errorResult, Resource dep, boolean replaceQuotes) {

		Script script = (Script) dep.getContents().get(0);
		if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(script)) {
			return;
		}

		String path = getPathFromImplementedByAnnotation(script);

		Optional<? extends IN4JSSourceContainer> sourceOpt = core.findN4JSSourceContainer(dep.getURI());
		if (sourceOpt.isPresent()) {
			IN4JSSourceContainer source = sourceOpt.get();
			IN4JSProject project = source.getProject();
			for (IN4JSSourceContainer c : project.getSourceContainers()) {
				if (c.isExternal()) {
					String sourceRelativePath = dep.getURI().toString()
							.replace(source.getLocation().toString(), "");
					String[] potentialExternalSourceRelativeURISegments = null;
					if (path == null || !path.contains("/")) {
						String potentialExternalSourceRelativePath = sourceRelativePath.replace(".n4jsd", ".js");
						potentialExternalSourceRelativeURISegments = URI.createURI(potentialExternalSourceRelativePath)
								.segments();
						if (path != null && !path.contains("/")
								&& potentialExternalSourceRelativeURISegments.length > 0) {
							potentialExternalSourceRelativeURISegments[potentialExternalSourceRelativeURISegments.length
									- 1] = path;
						}
					} else {
						if (!path.endsWith(".js")) {
							path += ".js";
						}
						potentialExternalSourceRelativeURISegments = path.split("/");
					}
					if (potentialExternalSourceRelativeURISegments != null) {
						URI potentialExternalSourceURI = c.getLocation().appendSegments(
								potentialExternalSourceRelativeURISegments);
						try {
							Resource externalDep = dep.getResourceSet().getResource(potentialExternalSourceURI, true);
							script = (Script) externalDep.getContents().get(0);
							if (isCompilable(externalDep, errorResult)) {
								createTempJsFileWithScript(script, replaceQuotes);
							}
						} catch (Exception e) {
							throw new RuntimeException("Couldn't load " + potentialExternalSourceURI + ".", e);
						}
					}
				}
			}
		}
	}

	private String getPathFromImplementedByAnnotation(Script depRoot) {
		String path = null;
		Annotation scriptAnno = AnnotationDefinition.IMPLEMENTED_BY.getAnnotation(depRoot);
		if (scriptAnno != null && scriptAnno.getArgs().size() == 1) {
			AnnotationArgument arg = scriptAnno.getArgs().get(0);
			if (arg instanceof LiteralAnnotationArgument) {
				Literal literal = ((LiteralAnnotationArgument) arg).getLiteral();
				if (literal instanceof StringLiteral) {
					path = ((StringLiteral) literal).getValue();
				}
			}
		}
		return path;
	}

	private File createTempJsFileWithScript(final Script script, final boolean replaceQuotes) throws IOException {
		// Compile script to get file content.
		final String content = compile(script, replaceQuotes);

		final String projectName = getProjectName(script);
		Path parentPath = createDirectory(getTempFolder(), projectName); // TODO IDE-

		// Get folder structure from qualified names.
		final LinkedList<String> segments = moduleQualifiedNameSegments(script);
		// Pop the file name from the segments.
		final String fileName = segments.removeLast();
		// Recursively create temporary folder structure.
		for (final String folderName : segments) {
			parentPath = createDirectory(parentPath, folderName);
		}

		final File file = new File(parentPath.toFile(), fileName + ".js");
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new RuntimeException("Exception when creating new file: " + file);
			}
		}

		file.deleteOnExit();
		try (final PrintWriter pw = new PrintWriter(file)) {
			pw.print(content); // Make sure not to append but override content.
		}

		return file;
	}

	/** Splits up the script's qualified name along the dots. */
	private LinkedList<String> moduleQualifiedNameSegments(final Script script) {
		return newLinkedList(on(".").split(script.getModule().getQualifiedName()));
	}

	private String getProjectName(final Script script) {
		// Cannot use script.module.projectName since all '.'-s are replaced with '_'-s.
		// See N4JSTypesBuilder.createTModuleFromSource
		final IN4JSProject project = core.findProject(script.eResource().getURI()).orNull();
		if (null == project) {
			throw new NullPointerException(
					"Cannot find project for script in resource: " + script.eResource().getURI());
		}
		return project.getProjectName();
	}

}
