/**
 */
package eu.numberfour.n4js.regex.regularExpression;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.RegularExpressionBody#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getRegularExpressionBody()
 * @model
 * @generated
 */
public interface RegularExpressionBody extends EObject
{
  /**
   * Returns the value of the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pattern</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pattern</em>' containment reference.
   * @see #setPattern(Pattern)
   * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getRegularExpressionBody_Pattern()
   * @model containment="true"
   * @generated
   */
  Pattern getPattern();

  /**
   * Sets the value of the '{@link eu.numberfour.n4js.regex.regularExpression.RegularExpressionBody#getPattern <em>Pattern</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pattern</em>' containment reference.
   * @see #getPattern()
   * @generated
   */
  void setPattern(Pattern value);

} // RegularExpressionBody
