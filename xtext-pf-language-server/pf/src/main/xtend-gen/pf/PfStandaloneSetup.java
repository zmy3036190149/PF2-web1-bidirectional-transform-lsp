/**
 * generated by Xtext 2.13.0
 */
package pf;

import pf.PfStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class PfStandaloneSetup extends PfStandaloneSetupGenerated {
  public static void doSetup() {
    new PfStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}