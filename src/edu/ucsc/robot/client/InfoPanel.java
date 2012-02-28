package edu.ucsc.robot.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.ucsc.robot.util.Messages;

/**
 * 
 * @author Huascar A. Sanchez
 *
 */
public class InfoPanel extends HTMLPanel {
  public InfoPanel() {
    super(Messages.DIV_HTML);
    DOM.setStyleAttribute(getElement(), "fontFamily", "Monospace");
    DOM.setStyleAttribute(getElement(), "fontSize", "12px");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#f0f0f0");
    DOM.setStyleAttribute(getElement(), "marginTop", "50px");
    DOM.setStyleAttribute(getElement(), "overflow", "hidden");
    DOM.setStyleAttribute(getElement(), "textAlign", "center");
    final HTMLPanel innerDiv = new HTMLPanel(Messages.HTML);
    getElement().appendChild(innerDiv.getElement());
  }

}
