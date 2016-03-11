/**
 */
package eu.numberfour.n4js.regex.regularExpression.impl;

import eu.numberfour.n4js.regex.regularExpression.CharacterClassAtom;
import eu.numberfour.n4js.regex.regularExpression.CharacterClassElement;
import eu.numberfour.n4js.regex.regularExpression.CharacterClassEscapeSequence;
import eu.numberfour.n4js.regex.regularExpression.EscapedCharacterClassAtom;
import eu.numberfour.n4js.regex.regularExpression.RegularExpressionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Character Class Escape Sequence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl#getCharacter <em>Character</em>}</li>
 *   <li>{@link eu.numberfour.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl#getSequence <em>Sequence</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CharacterClassEscapeSequenceImpl extends AtomEscapeImpl implements CharacterClassEscapeSequence
{
  /**
   * The default value of the '{@link #getCharacter() <em>Character</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCharacter()
   * @generated
   * @ordered
   */
  protected static final String CHARACTER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCharacter() <em>Character</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCharacter()
   * @generated
   * @ordered
   */
  protected String character = CHARACTER_EDEFAULT;

  /**
   * The default value of the '{@link #getSequence() <em>Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSequence()
   * @generated
   * @ordered
   */
  protected static final String SEQUENCE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSequence() <em>Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSequence()
   * @generated
   * @ordered
   */
  protected String sequence = SEQUENCE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CharacterClassEscapeSequenceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return RegularExpressionPackage.Literals.CHARACTER_CLASS_ESCAPE_SEQUENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCharacter()
  {
    return character;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCharacter(String newCharacter)
  {
    String oldCharacter = character;
    character = newCharacter;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER, oldCharacter, character));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSequence()
  {
    return sequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSequence(String newSequence)
  {
    String oldSequence = sequence;
    sequence = newSequence;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE, oldSequence, sequence));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER:
        return getCharacter();
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE:
        return getSequence();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER:
        setCharacter((String)newValue);
        return;
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE:
        setSequence((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER:
        setCharacter(CHARACTER_EDEFAULT);
        return;
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE:
        setSequence(SEQUENCE_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER:
        return CHARACTER_EDEFAULT == null ? character != null : !CHARACTER_EDEFAULT.equals(character);
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE:
        return SEQUENCE_EDEFAULT == null ? sequence != null : !SEQUENCE_EDEFAULT.equals(sequence);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == CharacterClassElement.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == CharacterClassAtom.class)
    {
      switch (derivedFeatureID)
      {
        case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER: return RegularExpressionPackage.CHARACTER_CLASS_ATOM__CHARACTER;
        default: return -1;
      }
    }
    if (baseClass == EscapedCharacterClassAtom.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == CharacterClassElement.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == CharacterClassAtom.class)
    {
      switch (baseFeatureID)
      {
        case RegularExpressionPackage.CHARACTER_CLASS_ATOM__CHARACTER: return RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTER;
        default: return -1;
      }
    }
    if (baseClass == EscapedCharacterClassAtom.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (character: ");
    result.append(character);
    result.append(", sequence: ");
    result.append(sequence);
    result.append(')');
    return result.toString();
  }

} //CharacterClassEscapeSequenceImpl
