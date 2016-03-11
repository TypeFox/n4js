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
package eu.numberfour.n4js.transpiler.es.transform

import com.google.inject.Inject
import eu.numberfour.n4js.n4JS.ExpressionStatement
import eu.numberfour.n4js.n4JS.N4FieldDeclaration
import eu.numberfour.n4js.n4JS.N4InterfaceDeclaration
import eu.numberfour.n4js.n4JS.RelationalOperator
import eu.numberfour.n4js.n4JS.Statement
import eu.numberfour.n4js.n4JS.VariableDeclaration
import eu.numberfour.n4js.transpiler.Transformation
import eu.numberfour.n4js.transpiler.assistants.TypeAssistant
import eu.numberfour.n4js.transpiler.es.assistants.BootstrapCallAssistant
import eu.numberfour.n4js.transpiler.im.SymbolTableEntry
import eu.numberfour.n4js.transpiler.utils.TranspilerUtils
import eu.numberfour.n4js.ts.types.TInterface

import static eu.numberfour.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension eu.numberfour.n4js.transpiler.utils.TranspilerUtils.*
import static extension eu.numberfour.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 */
class InterfaceDeclarationTransformation extends Transformation {

	@Inject BootstrapCallAssistant bootstrapCallAssistant;
	@Inject TypeAssistant typeAssistant;


	override assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
	}

	override assertPostConditions() {
		// none
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4InterfaceDeclaration, false).forEach[transformInterfaceDecl];
	}

	def private void transformInterfaceDecl(N4InterfaceDeclaration ifcDecl) {
		val ifcSTE = findSymbolTableEntryForElement(ifcDecl, true);

		val varDecl = createVarDecl(ifcDecl);
		val fieldInitFun = createInstanceFieldInitializationFunction(ifcDecl, ifcSTE);
		val staticFieldInits = createStaticFieldInitializations(ifcDecl, ifcSTE);
		val memberDefs = bootstrapCallAssistant.createInterfaceMemberDefinitionSection(ifcDecl);
		val makeIfcCall = bootstrapCallAssistant.createMakeInterfaceCall(ifcDecl);

		state.tracer.copyTrace(ifcDecl, staticFieldInits);
		state.tracer.copyTrace(ifcDecl, memberDefs);
		state.tracer.copyTrace(ifcDecl, makeIfcCall);

		replace(ifcDecl, varDecl);
		val root = varDecl.eContainer.orContainingExportDeclaration;
		insertAfter(root, #[fieldInitFun] + staticFieldInits + memberDefs + #[makeIfcCall]);

		state.info.markAsToHoist(varDecl);
	}

	/**
	 * Creates declaration of the variable that will represent the interface.
	 */
	def private VariableDeclaration createVarDecl(N4InterfaceDeclaration ifcDecl) {
		return _VariableDeclaration(ifcDecl.name)=>[
			expression = _ObjLit();
		];
	}

	def private ExpressionStatement createInstanceFieldInitializationFunction(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// I.$fieldInit = function I_fieldInit(target, spec, mixinExclusion) {
		//     // ...
		// };
		val $fieldInitSTE = steFor_$fieldInit;
		return _ExprStmnt(_AssignmentExpr()=>[
			lhs = _PropertyAccessExpr(ifcSTE, $fieldInitSTE);
			rhs = _FunExpr(false, ifcDecl.name + '_fieldInit', #[ _Fpar("target"), _Fpar("spec"), _Fpar("mixinExclusion") ])=>[
				body.statements += createInstanceFieldInitializations(ifcDecl);
				body.statements += createDelegationToFieldInitOfExtendedInterfaces(ifcDecl);
			];
		]);
	}

	def private Statement[] createInstanceFieldInitializations(N4InterfaceDeclaration ifcDecl) {
		// if(spec) {
		//      if(!(mixinExclusion.hasOwnProperty("field") || target.hasOwnProperty("field"))) {
		//     	    target.field = 'field' in spec ? spec.field : 42;
		//      }
		// } else {
		//     if(!(mixinExclusion.hasOwnProperty("field") || target.hasOwnProperty("field"))) {
		//         target.field = 42;
		//     }
		// }
		val fields = ifcDecl.ownedFields.filter[!static].toList;
		if(!fields.empty) {
			val hasOwnPropertySTE = getSymbolTableEntryForMember(state.G.objectType, "hasOwnProperty", false, false, true);
			val fieldInitsFromSpec = <Statement>newArrayList;
			val fieldInitsNormal = <Statement>newArrayList;
			for(field : fields) {
				val fieldSTE = findSymbolTableEntryForElement(field, true);
				// target.field = 'field' in spec ? spec.field : 42;
				val specStmnt = _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_Snippet("target"), fieldSTE),
					_ConditionalExpr(
						_RelationalExpr(_StringLiteralForSTE(fieldSTE), RelationalOperator.IN, _Snippet("spec")),
						_PropertyAccessExpr(_Snippet("spec"), fieldSTE),
						{if(field.expression!==null) copy(field.expression) else undefinedRef()}
					)
				));
				fieldInitsFromSpec += ifStmntMixinExclusionORtarget(hasOwnPropertySTE,fieldSTE,specStmnt);

				// if(!(mixinExclusion.hasOwnProperty("field") || target.hasOwnProperty("field"))) {target.field = 42;}
				val trueBody = _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_Snippet("target"), fieldSTE),
					{if(field.expression!==null) copy(field.expression) else undefinedRef()}
				));
				fieldInitsNormal += ifStmntMixinExclusionORtarget(hasOwnPropertySTE,fieldSTE,trueBody);
			}
			// if(spec) {...} else {...}
			return #[_IfStmnt(
				_Snippet("spec"),
				_Block(fieldInitsFromSpec),
				_Block(fieldInitsNormal)
			)];
		}
		return #[];
	}

	def private ifStmntMixinExclusionORtarget(SymbolTableEntry hasOwnPropertySTE, SymbolTableEntry fieldSTE, Statement trueBody ){
		return _IfStmnt(
			_NOT(_Parenthesis(_OR(
				_CallExpr(_PropertyAccessExpr(_Snippet("mixinExclusion"), hasOwnPropertySTE), _StringLiteralForSTE(fieldSTE)),
				_CallExpr(_PropertyAccessExpr(_Snippet("target"), hasOwnPropertySTE), _StringLiteralForSTE(fieldSTE))
			))),
			trueBody
		);
	}

	def private Statement[] createDelegationToFieldInitOfExtendedInterfaces(N4InterfaceDeclaration ifcDecl) {
		// I.$fieldInit(target, spec, mixinExclusion);
		val result = newArrayList;
		val $fieldInitSTE = steFor_$fieldInit;
		val superIfcSTEs = typeAssistant.getSuperInterfacesSTEs(ifcDecl).filter[
			// regarding the cast to TInterface: see preconditions above
			!TranspilerUtils.isDefSiteStructural(originalTarget as TInterface)
		];
		for(superIfcSTE : superIfcSTEs) {
			result += _ExprStmnt(_CallExpr(
				__NSSafe_PropertyAccessExpr(superIfcSTE, $fieldInitSTE),
				_Snippet("target"), _Snippet("spec"), _Snippet("mixinExclusion")
			));
		}
		return result;
	}

	def private ExpressionStatement[] createStaticFieldInitializations(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// for an interface 'I' with a static field 'field' we here create something like:
		// I.field = "initial value";
		return ifcDecl.ownedMembers.filter(N4FieldDeclaration).filter[static].filter[expression!==null].map[fieldDecl|
			_ExprStmnt(_AssignmentExpr()=>[
				lhs = _PropertyAccessExpr(ifcSTE, findSymbolTableEntryForElement(fieldDecl,true));
				rhs = fieldDecl.expression;
			]);
		];
	}
}
