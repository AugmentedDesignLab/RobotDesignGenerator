package edu.ucsc.robot.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.ucsc.robot.client.WorkplanePanel;

public class WorkspacePanel extends FocusPanel {
  private final Dashboard index;
  private int width;
  private int height;
  
  private WorkplanePanel workplane;  
  public WorkspacePanel(Dashboard index){
    this(index, new VerticalPanel());
  }
  
  public WorkspacePanel(Dashboard index, Widget container){
    super(container);
    this.index = index;
    
    width      = index.getScreenWidth()  - 350;
    height     = index.getScreenHeight() - 400;
    
    // In order to get rid of the FocusPanel's "annoying" border 
    // when hasFocus is set to true, I used CSS (outline: none)
    DOM.setStyleAttribute(getElement(), "outline", "none");
    
    setSize(width + "px", height + "px");
    
    
    addGlobalCamera();
    addGlobalCameraRotation();
    
    addWorkplane();    
  }
  
  
  public void addComponent(Widget widget){
    getWidearea().add(widget);
  }
  
  public void addWorkplane(){
    workplane = new WorkplanePanel(index, width, height); 
    addComponent(workplane);
  }
  
  public void addGlobalCamera(){
    addComponent(index.getGlobalCamera());
  }
  
  public void addGlobalCameraRotation(){
    addComponent(index.getGlobalCameraRotation());
  } 
  
  public Dashboard getDashboard(){
    return index;
  }
  
  public WorkplanePanel getWorkplane(){
    return workplane;
  }
  
  /**
   * @return the actual panel where all elements (in this order), such as
   *    view control, workplane, and side panels.
   */
  public VerticalPanel getWidearea(){
    return (VerticalPanel) getWidget();
  }  
  

}
