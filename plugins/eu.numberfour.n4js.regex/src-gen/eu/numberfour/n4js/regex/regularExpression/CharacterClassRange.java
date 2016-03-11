/**
 */
package eu.numberfour.n4js.regex.regularExpression;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Character Class Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.CharacterClassRange#getLeft <em>Left</em>}</li>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.CharacterClassRange#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange()
 * @model
 * @generated
 */
public interface CharacterClassRange extends CharacterClassElement
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(CharacterClassAtom)
   * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange_Left()
   * @model containment="true"
   * @generated
   */
  CharacterClassAtom getLeft();

  /**
   * Sets the value of the '{@link eu.numberfour.n4js.regex.regularExpression.CharacterClassRange#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(CharacterClassAtom value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(CharacterClassAtom)
   * @see eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange_Right()
   * @model containment="true"
   * @generated
   */
  CharacterClassAtom getRight();

  /**
   * Sets the value of the '{@link eu.numberfour.n4js.regex.regularExpression.CharacterClassRange#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(CharacterClassAtom value);

} // CharacterClassRange
