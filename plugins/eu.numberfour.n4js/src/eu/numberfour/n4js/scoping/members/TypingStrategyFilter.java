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
package eu.numberfour.n4js.scoping.members;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

import eu.numberfour.n4js.ts.types.ContainerType;
import eu.numberfour.n4js.ts.types.MemberAccessModifier;
import eu.numberfour.n4js.ts.types.NameAndAccess;
import eu.numberfour.n4js.ts.types.TField;
import eu.numberfour.n4js.ts.types.TGetter;
import eu.numberfour.n4js.ts.types.TMember;
import eu.numberfour.n4js.ts.types.TMethod;
import eu.numberfour.n4js.ts.types.TSetter;
import eu.numberfour.n4js.ts.types.TStructMember;
import eu.numberfour.n4js.ts.types.TypingStrategy;

/**
 * Filter used in {@link TypingStrategyAwareMemberScope} to filter out results not available for a given typing
 * strategy.
 */
class TypingStrategyFilter implements Predicate<IEObjectDescription> {

	final TypingStrategy typingStrategy;
	final boolean isLeftHand;

	TypingStrategyFilter(TypingStrategy typingStrategy, boolean isLeftHand) {
		this.typingStrategy = typingStrategy;
		this.isLeftHand = isLeftHand;
	}

	/**
	 * Returns the typing strategy of this filter, set in the constructor.
	 */
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean apply(IEObjectDescription description) {
		if (typingStrategy == TypingStrategy.DEFAULT || typingStrategy == TypingStrategy.NOMINAL) {
			return true;
		}
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance == null || proxyOrInstance.eIsProxy()) {
			return true;
		}
		if (!(proxyOrInstance instanceof TMember)) {
			return true;
		}
		TMember member = (TMember) proxyOrInstance;
		if (member.isStatic() || member.getMemberAccessModifier() != MemberAccessModifier.PUBLIC) {
			return false;
		}

		if (member instanceof TMethod) {
			switch (typingStrategy) {
			case STRUCTURAL:
				boolean hasMethod = !"constructor".equals(member.getName()) || member instanceof TStructMember;
				return hasMethod;
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_READ_ONLY_FIELDS:
			case STRUCTURAL_WRITE_ONLY_FIELDS:
			case STRUCTURAL_FIELD_INITIALIZER:
				return false;
			}
		}

		if (member instanceof TGetter) {
			switch (typingStrategy) {
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_READ_ONLY_FIELDS:
				return true;
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return false;
			case STRUCTURAL_FIELD_INITIALIZER:
				ContainerType<?> type = member.getContainingType();
				NameAndAccess naa = new NameAndAccess(member.getName(), true, false);
				Map<NameAndAccess, ? extends TMember> members = type.getOwnedMembersByNameAndAccess();
				boolean hasSetter = members.containsKey(naa);
				return hasSetter;
			}
		}

		if (member instanceof TSetter) {
			switch (typingStrategy) {
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return true;
			case STRUCTURAL_READ_ONLY_FIELDS:
			case STRUCTURAL_FIELD_INITIALIZER:
				return false;
			}
		}

		if (member instanceof TField) {
			TField field = (TField) member;
			switch (typingStrategy) {
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
				return true;
			case STRUCTURAL_READ_ONLY_FIELDS:
				return !isLeftHand;
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return isLeftHand;
			case STRUCTURAL_FIELD_INITIALIZER:
				boolean isAccessable = !isLeftHand && (!field.isFinal() || !field.isHasExpression());
				return isAccessable;
			}
		}

		return true;
	}

}
