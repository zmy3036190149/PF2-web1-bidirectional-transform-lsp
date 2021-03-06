/**
 * generated by Xtext 2.13.0
 */
package pf.pf;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see pf.pf.PfPackage
 * @generated
 */
public interface PfFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PfFactory eINSTANCE = pf.pf.impl.PfFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Problem Diagram</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Problem Diagram</em>'.
   * @generated
   */
  ProblemDiagram createProblemDiagram();

  /**
   * Returns a new object of class '<em>Node</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Node</em>'.
   * @generated
   */
  Node createNode();

  /**
   * Returns a new object of class '<em>Phenomenon</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phenomenon</em>'.
   * @generated
   */
  Phenomenon createPhenomenon();

  /**
   * Returns a new object of class '<em>Link</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Link</em>'.
   * @generated
   */
  Link createLink();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  PfPackage getPfPackage();

} //PfFactory
