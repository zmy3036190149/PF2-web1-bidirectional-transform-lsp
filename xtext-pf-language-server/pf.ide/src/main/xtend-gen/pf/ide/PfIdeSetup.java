/**
 * generated by Xtext 2.12.0-SNAPSHOT
 */
package pf.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import pf.PfRuntimeModule;
import pf.PfStandaloneSetup;
import pf.ide.PfIdeModule;

/**
 * Initialization support for running Xtext languages as language servers.
 */
@SuppressWarnings("all")
public class PfIdeSetup extends PfStandaloneSetup {
  @Override
  public Injector createInjector() {
    PfRuntimeModule _pfRuntimeModule = new PfRuntimeModule();
    PfIdeModule _pfIdeModule = new PfIdeModule();
    return Guice.createInjector(Modules2.mixin(_pfRuntimeModule, _pfIdeModule));
  }
}
