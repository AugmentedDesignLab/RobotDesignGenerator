/**
 * 
 */
package edu.ucsc.robot.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This represents the Robot Design Generator Dashboard...
 * @author Huascar A. Sanchez
 *
 */
public class MainScreen implements EntryPoint {
  @Override
  public void onModuleLoad() {
    final Dashboard dashboard = Dashboard.getInstance();
    RootPanel.get().add(dashboard);
  }
}
