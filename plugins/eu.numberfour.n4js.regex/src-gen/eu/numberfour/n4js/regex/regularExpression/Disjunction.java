/**
 */
package eu.numberfour.n4js.regex.regularExpression;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Disjunction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.Disjunction#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getDisjunction()
 * @model
 * @generated
 */
public interface Disjunction extends Pattern
{
  /**
   * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
   * The list contents are of type {@link eu.numberfour.n4js.regex.regularExpression.Pattern}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elements</em>' containment reference list.
   * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getDisjunction_Elements()
   * @model containment="true"
   * @generated
   */
  EList<Pattern> getElements();

} // Disjunction
