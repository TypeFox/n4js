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
package eu.numberfour.n4js.ui.contentassist;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * Overrides the default {@link org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory} to use the
 * API that is introduced by the {@link CustomN4JSParser}.
 *
 * All overridden methods basically override the inherited behavior to use the production parser's node model rather
 * than a bogus own lexer.
 *
 * @see CustomN4JSParser#getFollowElements(INode, int, int, boolean)
 */
public class ContentAssistContextFactory
		extends org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory {

	@Override
	public void setPool(ExecutorService pool) {
		super.setPool(pool);
	}

	@Override
	public CustomN4JSParser getParser() {
		return (CustomN4JSParser) super.getParser();
	}

	@Override
	protected void createContextsForLastCompleteNode(EObject previousModel, boolean strict) {
		String currentNodePrefix = getPrefix(currentNode);
		if (!Strings.isEmpty(currentNodePrefix) && !currentNode.getText().equals(currentNodePrefix)) {
			lexer.setCharStream(new ANTLRStringStream(currentNodePrefix));
			Token token = lexer.nextToken();
			if (token == Token.EOF_TOKEN) { // error case - nothing could be parsed
				return;
			}
			while (token != Token.EOF_TOKEN) {
				if (isErrorToken(token))
					return;
				token = lexer.nextToken();
			}
		}
		String prefix = "";
		Collection<FollowElement> followElements = getParser().getFollowElements(rootNode, 0, completionOffset, strict);
		doCreateContexts(lastCompleteNode, currentNode, prefix, previousModel, followElements);
	}

	@Override
	protected void handleLastCompleteNodeAsPartOfDatatypeNode() {
		String prefix = getPrefix(datatypeNode);
		Collection<FollowElement> followElements = getParser().getFollowElements(rootNode, 0, datatypeNode.getOffset(),
				true);
		INode lastCompleteNodeBeforeDatatype = getLastCompleteNodeByOffset(rootNode, datatypeNode.getTotalOffset());
		doCreateContexts(lastCompleteNodeBeforeDatatype, datatypeNode, prefix, currentModel, followElements);
	}

	@Override
	protected void handleLastCompleteNodeIsAtEndOfDatatypeNode() {
		String prefix = getPrefix(lastCompleteNode);
		INode previousNode = getLastCompleteNodeByOffset(rootNode, lastCompleteNode.getOffset());
		EObject previousModel = previousNode.getSemanticElement();
		INode currentDatatypeNode = getContainingDatatypeRuleNode(currentNode);
		Collection<FollowElement> followElements = getParser().getFollowElements(rootNode, 0,
				lastCompleteNode.getOffset(), true);
		int prevSize = contextBuilders.size();
		doCreateContexts(previousNode, currentDatatypeNode, prefix, previousModel, followElements);

		if (lastCompleteNode instanceof ILeafNode && lastCompleteNode.getGrammarElement() == null
				&& contextBuilders.size() != prevSize) {
			handleLastCompleteNodeHasNoGrammarElement(contextBuilders.subList(prevSize, contextBuilders.size()),
					previousModel);
		}
	}

//	/**
//	 * Copied from {@link ParserBasedContentAssistContextFactory} and changed so that it uses our
//	 * {@link N4JSFollowElementCalculcator} and {@link N4JSFollowElementAcceptor}. No other functional changes.
//	 *
//	 * <p>
//	 * Unfortunately, we have to override this method and copy the super implementation to provide our own subclass of
//	 * {@link N4JSFollowElementCalculcator} and set its follow element acceptor. The acceptor used in the super class is
//	 * an anonymous class, an instance of which is then assigned to a protected field of {@link FollowElementCalculator}
//	 * that we cannot access here. That's why we have to pass our own acceptor to the constructor of
//	 * {@link N4JSFollowElementCalculcator} and assign it to the protected field there. To add insult to injury, the
//	 * overridden method accesses <code>parameterConfig</code>, a private field of {@link FollowElementCalculator},
//	 * which again, we cannot do in our overriding method. Here, we have to resort to reflection to set the private
//	 * field of the super class in {@link N4JSFollowElementCalculcator#setParameterConfig(int)}.
//	 * </p>
//	 *
//	 * TODO: Revise when https://github.com/eclipse/xtext-core/issues/120 is fixed. Then, we only need to override a
//	 * factory method to create our subclass of {@link FollowElementCalculator}.
//	 */
//	@Override
//	protected void computeFollowElements(Collection<FollowElement> followElements, Collection<AbstractElement> result) {
//		N4JSFollowElementCalculcator calculator = new N4JSFollowElementCalculcator(
//				new N4JSFollowElementAcceptor(result));
//
//		for (FollowElement element : followElements) {
//			List<Integer> paramStack = element.getParamStack();
//			if (!paramStack.isEmpty()) {
//				calculator.setParameterConfig(paramStack.get(paramStack.size() - 1));
//			} else {
//				calculator.setParameterConfig(0);
//			}
//
//			computeFollowElements(calculator, element);
//		}
//	}
//
//	/**
//	 * We subclass {@link FollowElementCalculator} here in order to filter out any follow elements that would be added
//	 * as a result of a rule call to any rule whose name begins with "Bogus". This is necessary because any proposal
//	 * that is derived from such a follow element is bogus itself and should not be shown to the user. Unfortunately, it
//	 * is impossible to trace back whether or not a proposal was based on a bogus grammar element, so we have to do it
//	 * here instead of later on in the proposal providers.
//	 *
//	 * <p>
//	 * Since the base class was not intended for subclassing, we have to resort to some reflection trickery in order to
//	 * access some of its properties.
//	 * </p>
//	 *
//	 * TODO: Remove {@link N4JSFollowElementCalculcator#N4JSFollowElementCalculcator(IFollowElementAcceptor)} and
//	 * {@link N4JSFollowElementCalculcator#setParameterConfig(int)} once
//	 * https://github.com/eclipse/xtext-core/issues/120 is fixed. We only need them because we had to override and copy
//	 * method {@link ContentAssistContextFactory#computeFollowElements(Collection, Collection)}.
//	 */
//	private static class N4JSFollowElementCalculcator extends FollowElementCalculator {
//		public N4JSFollowElementCalculcator(IFollowElementAcceptor acceptor) {
//			this.acceptor = acceptor;
//		}
//
//		// the following shouldn't be needed anymore as available via API
//		// public void setParameterConfig(int parameterConfig) {
//		// try {
//		// Field field = FollowElementCalculator.class.getDeclaredField("parameterConfig");
//		// field.setAccessible(true);
//		// field.set(this, parameterConfig);
//		// } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//		// throw new RuntimeException(e);
//		// }
//		// }
//
//		@Override
//		public Boolean caseParserRule(ParserRule parserRule) {
//			if (!parserRule.getName().startsWith("Bogus"))
//				return super.caseParserRule(parserRule);
//			return Boolean.FALSE;
//		}
//	}
//
//	/**
//	 * Copied from {@link ParserBasedContentAssistContextFactory} without any functional changes.
//	 *
//	 * TODO: Remove once https://github.com/eclipse/xtext-core/issues/120 is fixed.
//	 */
//	private static class N4JSFollowElementAcceptor implements IFollowElementAcceptor {
//		private final Collection<AbstractElement> result;
//
//		public N4JSFollowElementAcceptor(Collection<AbstractElement> result) {
//			this.result = result;
//		}
//
//		@Override
//		public void accept(AbstractElement element) {
//			ParserRule rule = GrammarUtil.containingParserRule(element);
//			if (rule == null || !GrammarUtil.isDatatypeRule(rule))
//				result.add(element);
//		}
//
//	}
}
