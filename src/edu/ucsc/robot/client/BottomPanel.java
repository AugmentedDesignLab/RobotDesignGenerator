/**
 * 
 */
package edu.ucsc.robot.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Huascar A. Sanchez
 * TODO refactor once layout is fixed...
 */
public class BottomPanel extends VerticalPanel {
  private final HorizontalPanel buttons;
      
  public BottomPanel() {
    super();
    // In order to get rid of the FocusPanel's "annoying" border 
    // when hasFocus is set to true, I used CSS (outline: none)
    DOM.setStyleAttribute(getElement(), "outline", "none");
    DOM.setStyleAttribute(getElement(), "position", "absolute");
    DOM.setStyleAttribute(getElement(), "color", "#444");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#fff");
    DOM.setStyleAttribute(getElement(), "borderTop", "1px solid #ddd");
    DOM.setStyleAttribute(getElement(), "padding", "8px 10px");
    DOM.setStyleAttribute(getElement(), "bottom", "5px");
    
    
    buttons = new HorizontalPanel();
    buttons.setSpacing(5);
    add(buttons);
    setCellWidth(buttons, "100%");
    buttons.add(new Button("Help", new ClickHandler(){

      @Override
      public void onClick(ClickEvent event) {
        Window.alert("Help is coming for you!");
      }}));
    
    buttons.add(new Button("Generate", new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        Window.alert("Generating Robot Design!");
      }
    }));
    
    buttons.add(new Button("Print 3D", new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        Window.alert("Printing Robot Design in 3D!");
      }
    }));
  }
   

}
