/**
 * 
 */
package edu.ucsc.robot.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * a toolbox panel is a container where the {@link ChassisPanel}, {@link WheelPanel}, 
 * and {@code SensorPanel} will be placed.
 * @author Huascar A. Sanchez
 *
 */
public class ToolboxPanel extends PopupPanel {
  private final Dashboard     index;
  private final VerticalPanel root;
  private final Button        hideButton;
  private final Label         controlLabel;
  private final ChassisPanel  chassis;
  private final WheelPanel    wheel;
  
  public ToolboxPanel(Dashboard index){
    super();
    
    this.index = index;
    root = new VerticalPanel();
    root.setSpacing(2);
    
    DOM.setStyleAttribute(root.getElement(), "fontFamily", "Monospace");
    DOM.setStyleAttribute(root.getElement(), "fontSize", "12px");
    DOM.setStyleAttribute(root.getElement(), "backgroundColor", "#f0f0f0"); 
    
    controlLabel = new Label("Toolbox");
    DOM.setStyleAttribute(controlLabel.getElement(), "fontWeight", "bold");
    controlLabel.setStyleName("title");
    
    add(root);
    
    root.add(controlLabel);
    
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setWidth("100%");
    hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    root.add(hPanel);
    hideButton = new Button("Hide Control");

    hideButton.setVisible(false);
    hPanel.add(hideButton);
    
    chassis = ChassisPanel.getInstance();    
    wheel   = WheelPanel.getInstance();
    
    chassis.setVisible(false);
    wheel.setVisible(false);
    
    root.add(chassis);
    root.add(wheel);   
    
    addLabelClickHandler(new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        showPanels();
      }
     }
    );
    
    addButtonClickHandler(new ClickHandler(){

      @Override
      public void onClick(ClickEvent event) {
        hidePanels();
      }
      }
    );      
  }
    
  public void addButtonClickHandler(ClickHandler handler){
    hideButton.addClickHandler(handler);
  }
  
  public void addLabelClickHandler(ClickHandler handler){
    controlLabel.addClickHandler(handler);
  }
  
  public void displayOnRightTopCorner(){
//    show();
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (index.getScreenWidth() - offsetWidth) / 3 + 600;
        int top = (index.getScreenHeight() - offsetHeight) / 3 - 150;
        setPopupPosition(left, top);
      }
    });    
  }
  
  
  public Dashboard getDashboard(){
    return index;
  }
  
  public void showPanels(){
    chassis.setVisible(true);
    wheel.setVisible(true);
    showHiddenButton();
  }
  
  public void hidePanels(){
    chassis.setVisible(false);
    wheel.setVisible(false);  
    hideHiddenButton();
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (index.getScreenWidth() - offsetWidth) / 3 + 600;
        int top = (index.getScreenHeight() - offsetHeight) / 3 - 150;
        setPopupPosition(left, top);
      }
    });     

  }
  
  public void showHiddenButton(){
    hideButton.setVisible(true);
  }
  
  public void hideHiddenButton(){
    hideButton.setVisible(false);
  }

}
