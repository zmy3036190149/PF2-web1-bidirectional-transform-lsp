/**
 * generated by Xtext 2.13.0
 */
package pf.pf;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Problem Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link pf.pf.ProblemDiagram#getName <em>Name</em>}</li>
 *   <li>{@link pf.pf.ProblemDiagram#getHighlight <em>Highlight</em>}</li>
 *   <li>{@link pf.pf.ProblemDiagram#getNodes <em>Nodes</em>}</li>
 *   <li>{@link pf.pf.ProblemDiagram#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @see pf.pf.PfPackage#getProblemDiagram()
 * @model
 * @generated
 */
public interface ProblemDiagram extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see pf.pf.PfPackage#getProblemDiagram_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link pf.pf.ProblemDiagram#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Highlight</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Highlight</em>' reference.
   * @see #setHighlight(Node)
   * @see pf.pf.PfPackage#getProblemDiagram_Highlight()
   * @model
   * @generated
   */
  Node getHighlight();

  /**
   * Sets the value of the '{@link pf.pf.ProblemDiagram#getHighlight <em>Highlight</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Highlight</em>' reference.
   * @see #getHighlight()
   * @generated
   */
  void setHighlight(Node value);

  /**
   * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
   * The list contents are of type {@link pf.pf.Node}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nodes</em>' containment reference list.
   * @see pf.pf.PfPackage#getProblemDiagram_Nodes()
   * @model containment="true"
   * @generated
   */
  EList<Node> getNodes();

  /**
   * Returns the value of the '<em><b>Links</b></em>' containment reference list.
   * The list contents are of type {@link pf.pf.Link}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Links</em>' containment reference list.
   * @see pf.pf.PfPackage#getProblemDiagram_Links()
   * @model containment="true"
   * @generated
   */
  EList<Link> getLinks();

} // ProblemDiagram
