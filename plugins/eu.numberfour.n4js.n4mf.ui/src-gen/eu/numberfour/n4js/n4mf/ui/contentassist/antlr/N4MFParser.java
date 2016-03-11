/*
 * generated by Xtext
 */
package eu.numberfour.n4js.n4mf.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import eu.numberfour.n4js.n4mf.services.N4MFGrammarAccess;

public class N4MFParser extends AbstractContentAssistParser {
	
	@Inject
	private N4MFGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected eu.numberfour.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser createParser() {
		eu.numberfour.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser result = new eu.numberfour.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getVersionConstraintAccess().getAlternatives(), "rule__VersionConstraint__Alternatives");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0(), "rule__VersionConstraint__Alternatives_0_0");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2(), "rule__VersionConstraint__Alternatives_0_2");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2(), "rule__VersionConstraint__Alternatives_0_2_0_2");
					put(grammarAccess.getN4mfIdentifierAccess().getAlternatives(), "rule__N4mfIdentifier__Alternatives");
					put(grammarAccess.getProjectTypeAccess().getAlternatives(), "rule__ProjectType__Alternatives");
					put(grammarAccess.getSourceFragmentTypeAccess().getAlternatives(), "rule__SourceFragmentType__Alternatives");
					put(grammarAccess.getModuleFilterTypeAccess().getAlternatives(), "rule__ModuleFilterType__Alternatives");
					put(grammarAccess.getProjectDependencyScopeAccess().getAlternatives(), "rule__ProjectDependencyScope__Alternatives");
					put(grammarAccess.getModuleLoaderAccess().getAlternatives(), "rule__ModuleLoader__Alternatives");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_0(), "rule__ProjectDescription__Group_0__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_1(), "rule__ProjectDescription__Group_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_2(), "rule__ProjectDescription__Group_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_3(), "rule__ProjectDescription__Group_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_4(), "rule__ProjectDescription__Group_4__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_5(), "rule__ProjectDescription__Group_5__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_6(), "rule__ProjectDescription__Group_6__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_11(), "rule__ProjectDescription__Group_11__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_15(), "rule__ProjectDescription__Group_15__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16(), "rule__ProjectDescription__Group_16__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16_3(), "rule__ProjectDescription__Group_16_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_17(), "rule__ProjectDescription__Group_17__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_17_3(), "rule__ProjectDescription__Group_17_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_18(), "rule__ProjectDescription__Group_18__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_19(), "rule__ProjectDescription__Group_19__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_21(), "rule__ProjectDescription__Group_21__0");
					put(grammarAccess.getExecModuleAccess().getGroup(), "rule__ExecModule__Group__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup(), "rule__TestedProjects__Group__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup_3(), "rule__TestedProjects__Group_3__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup_3_1(), "rule__TestedProjects__Group_3_1__0");
					put(grammarAccess.getInitModulesAccess().getGroup(), "rule__InitModules__Group__0");
					put(grammarAccess.getInitModulesAccess().getGroup_3(), "rule__InitModules__Group_3__0");
					put(grammarAccess.getInitModulesAccess().getGroup_3_1(), "rule__InitModules__Group_3_1__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup(), "rule__ImplementedProjects__Group__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup_3(), "rule__ImplementedProjects__Group_3__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup_3_1(), "rule__ImplementedProjects__Group_3_1__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup(), "rule__ProjectDependencies__Group__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup_3(), "rule__ProjectDependencies__Group_3__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup_3_1(), "rule__ProjectDependencies__Group_3_1__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup(), "rule__ProvidedRuntimeLibraries__Group__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3(), "rule__ProvidedRuntimeLibraries__Group_3__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1(), "rule__ProvidedRuntimeLibraries__Group_3_1__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup(), "rule__RequiredRuntimeLibraries__Group__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3(), "rule__RequiredRuntimeLibraries__Group_3__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1(), "rule__RequiredRuntimeLibraries__Group_3_1__0");
					put(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup(), "rule__ExtendedRuntimeEnvironment__Group__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup(), "rule__DeclaredVersion__Group__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1(), "rule__DeclaredVersion__Group_1__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1_2(), "rule__DeclaredVersion__Group_1_2__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_2(), "rule__DeclaredVersion__Group_2__0");
					put(grammarAccess.getSourceFragmentAccess().getGroup(), "rule__SourceFragment__Group__0");
					put(grammarAccess.getSourceFragmentAccess().getGroup_3(), "rule__SourceFragment__Group_3__0");
					put(grammarAccess.getModuleFilterAccess().getGroup(), "rule__ModuleFilter__Group__0");
					put(grammarAccess.getModuleFilterAccess().getGroup_3(), "rule__ModuleFilter__Group_3__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup(), "rule__BootstrapModule__Group__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup_1(), "rule__BootstrapModule__Group_1__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup(), "rule__ModuleFilterSpecifier__Group__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1(), "rule__ModuleFilterSpecifier__Group_1__0");
					put(grammarAccess.getProjectDependencyAccess().getGroup(), "rule__ProjectDependency__Group__0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getGroup(), "rule__SimpleProjectDescription__Group__0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0(), "rule__SimpleProjectDescription__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0(), "rule__VersionConstraint__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0(), "rule__VersionConstraint__Group_0_2_0__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_12(), "rule__N4mfIdentifier__Group_12__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_16(), "rule__N4mfIdentifier__Group_16__0");
					put(grammarAccess.getProjectDescriptionAccess().getArtifactIdAssignment_0_2(), "rule__ProjectDescription__ArtifactIdAssignment_0_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectNameAssignment_1_2(), "rule__ProjectDescription__ProjectNameAssignment_1_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_2_2(), "rule__ProjectDescription__ProjectTypeAssignment_2_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_3_2(), "rule__ProjectDescription__ProjectVersionAssignment_3_2");
					put(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_4_2(), "rule__ProjectDescription__DeclaredVendorIdAssignment_4_2");
					put(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_5_2(), "rule__ProjectDescription__VendorNameAssignment_5_2");
					put(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_6_2(), "rule__ProjectDescription__MainModuleAssignment_6_2");
					put(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_7(), "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_7");
					put(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_8(), "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_8");
					put(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_9(), "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_9");
					put(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_10(), "rule__ProjectDescription__ProjectDependenciesAssignment_10");
					put(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_11_2(), "rule__ProjectDescription__ImplementationIdAssignment_11_2");
					put(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_12(), "rule__ProjectDescription__ImplementedProjectsAssignment_12");
					put(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_13(), "rule__ProjectDescription__InitModulesAssignment_13");
					put(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_14(), "rule__ProjectDescription__ExecModuleAssignment_14");
					put(grammarAccess.getProjectDescriptionAccess().getOutputPathAssignment_15_2(), "rule__ProjectDescription__OutputPathAssignment_15_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_16_2(), "rule__ProjectDescription__LibraryPathsAssignment_16_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_16_3_1(), "rule__ProjectDescription__LibraryPathsAssignment_16_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_17_2(), "rule__ProjectDescription__ResourcePathsAssignment_17_2");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_17_3_1(), "rule__ProjectDescription__ResourcePathsAssignment_17_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_18_2(), "rule__ProjectDescription__SourceFragmentAssignment_18_2");
					put(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_19_2(), "rule__ProjectDescription__ModuleFiltersAssignment_19_2");
					put(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_20(), "rule__ProjectDescription__TestedProjectsAssignment_20");
					put(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_21_2(), "rule__ProjectDescription__ModuleLoaderAssignment_21_2");
					put(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3(), "rule__ExecModule__ExecModuleAssignment_3");
					put(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0(), "rule__TestedProjects__TestedProjectsAssignment_3_0");
					put(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1(), "rule__TestedProjects__TestedProjectsAssignment_3_1_1");
					put(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0(), "rule__InitModules__InitModulesAssignment_3_0");
					put(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1(), "rule__InitModules__InitModulesAssignment_3_1_1");
					put(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0(), "rule__ImplementedProjects__ImplementedProjectsAssignment_3_0");
					put(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1(), "rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1");
					put(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0(), "rule__ProjectDependencies__ProjectDependenciesAssignment_3_0");
					put(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1(), "rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0(), "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1(), "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0(), "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1(), "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1");
					put(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3(), "rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3");
					put(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0(), "rule__DeclaredVersion__MajorAssignment_0");
					put(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1(), "rule__DeclaredVersion__MinorAssignment_1_1");
					put(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1(), "rule__DeclaredVersion__MicroAssignment_1_2_1");
					put(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1(), "rule__DeclaredVersion__QualifierAssignment_2_1");
					put(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0(), "rule__SourceFragment__SourceFragmentTypeAssignment_0");
					put(grammarAccess.getSourceFragmentAccess().getPathsAssignment_2(), "rule__SourceFragment__PathsAssignment_2");
					put(grammarAccess.getSourceFragmentAccess().getPathsAssignment_3_1(), "rule__SourceFragment__PathsAssignment_3_1");
					put(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0(), "rule__ModuleFilter__ModuleFilterTypeAssignment_0");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2(), "rule__ModuleFilter__ModuleSpecifiersAssignment_2");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1(), "rule__ModuleFilter__ModuleSpecifiersAssignment_3_1");
					put(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1(), "rule__BootstrapModule__SourcePathAssignment_1_1");
					put(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1(), "rule__ModuleFilterSpecifier__SourcePathAssignment_1_1");
					put(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment(), "rule__ProvidedRuntimeLibraryDependency__ProjectAssignment");
					put(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment(), "rule__RequiredRuntimeLibraryDependency__ProjectAssignment");
					put(grammarAccess.getTestedProjectAccess().getProjectAssignment(), "rule__TestedProject__ProjectAssignment");
					put(grammarAccess.getProjectReferenceAccess().getProjectAssignment(), "rule__ProjectReference__ProjectAssignment");
					put(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0(), "rule__ProjectDependency__ProjectAssignment_0");
					put(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1(), "rule__ProjectDependency__VersionConstraintAssignment_1");
					put(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2(), "rule__ProjectDependency__DeclaredScopeAssignment_2");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0(), "rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getArtifactIdAssignment_1(), "rule__SimpleProjectDescription__ArtifactIdAssignment_1");
					put(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0(), "rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0");
					put(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1(), "rule__VersionConstraint__LowerVersionAssignment_0_1");
					put(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1(), "rule__VersionConstraint__UpperVersionAssignment_0_2_0_1");
					put(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0(), "rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0");
					put(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1(), "rule__VersionConstraint__LowerVersionAssignment_1");
					put(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), "rule__ProjectDescription__UnorderedGroup");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			eu.numberfour.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser typedParser = (eu.numberfour.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser) parser;
			typedParser.entryRuleProjectDescription();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public N4MFGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(N4MFGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
