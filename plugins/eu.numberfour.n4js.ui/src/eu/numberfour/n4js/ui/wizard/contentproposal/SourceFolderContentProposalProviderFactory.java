package eu.numberfour.n4js.ui.wizard.contentproposal;

import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;

import com.google.inject.Inject;

import eu.numberfour.n4js.projectModel.IN4JSCore;
import eu.numberfour.n4js.projectModel.IN4JSProject;

/**
 * A content proposal provider to propose source folders in a given project.
 */
public class SourceFolderContentProposalProviderFactory {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Returns a source folder proposal provider for the given project.
	 *
	 * @param contextProject
	 *            The project to look for source folders
	 * @return The provider
	 */
	public IContentProposalProvider createProviderForProject(IProject contextProject) {
		if (null == contextProject) {
			return null;
		}

		IN4JSProject n4Project = StreamSupport.stream(n4jsCore.findAllProjects().spliterator(), false)
				.filter(project -> project.getProjectId().equals(contextProject.getName())) // Filter for the context
																							// project
				.findAny().orElse(null);

		if (n4Project == null) {
			return null;
		}
		SimpleContentProposalProvider provider = new SimpleContentProposalProvider(
				n4Project.getSourceContainers().stream()
						.map(src -> src.getRelativeLocation())
						.toArray(String[]::new));
		provider.setFiltering(true);

		return provider;
	}
}