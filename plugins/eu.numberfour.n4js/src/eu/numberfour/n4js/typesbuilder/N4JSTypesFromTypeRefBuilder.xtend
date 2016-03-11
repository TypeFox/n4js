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
package eu.numberfour.n4js.typesbuilder;

import eu.numberfour.n4js.ts.typeRefs.FunctionTypeExpression
import eu.numberfour.n4js.ts.typeRefs.StructuralTypeRef
import eu.numberfour.n4js.ts.types.TModule
import eu.numberfour.n4js.ts.types.TypesFactory
import eu.numberfour.n4js.ts.utils.TypeUtils

/**
 * Methods for creating types from TypeRefs are collected in this class.
 * <p>
 * The methods of this class might seem different from other createXYZ() methods in the types
 * builder package, in that they take a subclass of TypeRef and not a typical AST node element.
 * However, SturcturalTypeRefs and FunctionTypeExpressions are among those TypeRefs that
 * <em>may</em> appear in the AST and play the role of an AST node. The methods in this class
 * will only be invoked for such TypeRefs that appear in the AST, so these method are, in fact,
 * very similar to the other createXYZ() methods.
 */
public class N4JSTypesFromTypeRefBuilder {

	/**
	 * Creates a TStructuralType in the target module if the StructuralTypeRef has structural
	 * members defined (in the with-clause). For more details why this is required, see API
	 * doc of StructuralTypeRef.
	 */
	def package void createStructuralType(StructuralTypeRef structTypeRef, TModule target) {

		if (!structTypeRef.astStructuralMembers.empty) {

			val structType = TypesFactory.eINSTANCE.createTStructuralType;

			structType.ownedMembers.addAll(
				structTypeRef.astStructuralMembers.map [ currStructMember |
					val clone = TypeUtils.copyWithProxies(currStructMember);
					clone.astElement = currStructMember;
					currStructMember.definedMember = clone;
					return clone;
				]);

			structTypeRef.structuralType = structType;

			target.internalTypes += structType;

		}
	}


	/**
	 * Creates a TFunction in the target module if the FunctionTypeExpression is generic.
	 * For more details why this is required, see API doc of FunctionTypeExpression.
	 */
	def package void createTFunction(FunctionTypeExpression fte, TModule target) {

		if (fte.generic) {

			val ft = TypesFactory.eINSTANCE.createTFunction();

			ft.typeVars.addAll(fte.typeVars.map[currTypeVar|TypeUtils.copyWithProxies(currTypeVar)]); // TODO support hyper links
			ft.fpars.addAll(fte.fpars.map[currFpar|
				val clone = TypeUtils.copyWithProxies(currFpar);
				clone.astElement = currFpar;
				return clone;
			]);
			ft.returnTypeRef = TypeUtils.copyWithProxies(fte.returnTypeRef);
			ft.declaredThisType = TypeUtils.copyWithProxies(fte.declaredThisType);

			fte.declaredType = ft;
			ft.astElement = fte;

			target.internalTypes += ft;

		}
	}
}
