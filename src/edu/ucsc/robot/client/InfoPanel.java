package edu.ucsc.robot.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;

public class InfoPanel extends HTMLPanel {
  private static final String INFO = "<strong>0 - 9</strong>: colors, <strong>click</strong>: " 
      + "add voxel, <strong>shift + click</strong>: remove voxel, " 
      + "<strong>shift + drag</strong>: rotate ";
  
  private static final String HTML = "<div "
      + "style='position:absolute; top:5px; width:100%'; text-align:center>"
      + "<span style='color: #444; background-color: #fff; border-bottom: 1px solid #ddd; " 
      + "padding: 8px 10px; text-transform: uppercase;'>" 
      + INFO
      +"</span>"
      + "</div>"; 
  
  private static final String DIV_HTML = "<div></div>";

  public InfoPanel() {
    super(DIV_HTML);
    DOM.setStyleAttribute(getElement(), "fontFamily", "Monospace");
    DOM.setStyleAttribute(getElement(), "fontSize", "12px");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#f0f0f0");
    DOM.setStyleAttribute(getElement(), "marginTop", "50px");
    DOM.setStyleAttribute(getElement(), "overflow", "hidden");
    DOM.setStyleAttribute(getElement(), "textAlign", "center");
    final HTMLPanel innerDiv = new HTMLPanel(HTML);
    getElement().appendChild(innerDiv.getElement());
  }

}
