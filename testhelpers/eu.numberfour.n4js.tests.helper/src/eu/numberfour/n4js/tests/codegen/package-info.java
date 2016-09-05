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
/**
 * This package contains a code generator for N4JS projects. It supports generating manifest files, classes, interfaces,
 * and their members. The artifacts to be generated are set up programmatically using a simple variant of the builder
 * pattern, whereby each class in this package functions as its own builder. Once all artifacts have been created and
 * configured, they can be generated by means of the their <code>generate</code> method or they can be created on the
 * file system by calling the <code>create</code> method.
 * <p>
 * The intended use of this generator is for test cases, particularly test cases for validation where only the structure
 * of a project and the types within are relevant. It is possible to specify method bodies, but there is no special
 * support for generating at that level of granularity.
 * </p>
 */
package eu.numberfour.n4js.tests.codegen;
