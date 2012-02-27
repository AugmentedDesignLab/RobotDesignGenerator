/**
 * 
 */
package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.core.Intersect;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Huascar A. Sanchez
 *
 */
public class WorkspacePanel extends FocusPanel implements RequiresResize, ProvidesResize {
  private final Dashboard       index;
  
  private int width;
  private int height;
  
  private WorkplanePanel workplane;
  
  public WorkspacePanel(Dashboard index){
    this(new VerticalPanel(), index);   
  }
  
  public WorkspacePanel(VerticalPanel panel, Dashboard index){
    super(panel);
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
    
    addKeyDownHandler(this.index);
    addKeyUpHandler(this.index);
    addKeyPressHandler(this.index);
    addFocusHandler(this.index);
    addMouseDownHandler(this.index);
    addMouseOutHandler(this.index);
    addMouseUpHandler(this.index);
    addMouseMoveHandler(this.index);
    addMouseWheelHandler(this.index);    
    
    // Set focus on the widget. We have to use a deferred command or a
    // timer since GWT will lose it again if we set it in-line here
    Scheduler.get().scheduleDeferred(new ScheduledCommand(){
      @Override
      public void execute() {
        setFocus(true);
      }
     }
    );    
    
  }
  
  /**
   * @return the actual panel where all elements (in this order), such as
   *    view control, workplane, and side panels.
   */
  public VerticalPanel getWidearea(){
    return (VerticalPanel) getWidget();
  }
  
  public Dashboard getDashboard(){
    return index;
  }
  
  public void unselectObjectOnPlane(){
    workplane.unselect();
  }
  
  public void dragSelectedObject(int x, int y){
    workplane.dragSelectedObject(x, y);
    workplane.printRotationAngle();
  }
  
  public JsArray<Intersect> locateObjectOnPlane(int x, int y){
    return workplane.locateObjectOnPlane(x, y);
  }
  
  public void selectObjectOnPlane(int x, int y){
    workplane.selectObjectOnPlane(x, y);
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

  @Override
  public void onResize() {
    if (getWidget() instanceof RequiresResize) {
      ((RequiresResize)getWidget()).onResize();
      index.setDimensions(Window.getClientWidth(), Window.getClientHeight());
    }   
  }
}
