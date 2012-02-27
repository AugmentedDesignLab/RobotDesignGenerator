/**
 * 
 */
package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.gwt.core.CameraControler;
import com.akjava.gwt.three.client.ui.CameraMoveWidget;
import com.akjava.gwt.three.client.ui.CameraRotationWidget;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Skeletal implementation of Dashboard.
 * @author huascarsanchez
 *
 */
public abstract class AbstractDashboard extends Composite implements 
KeyDownHandler, KeyUpHandler, FocusHandler, 
MouseDownHandler, MouseOutHandler, MouseMoveHandler, 
MouseWheelHandler, MouseUpHandler, KeyPressHandler {
  
  private static final String NOTHING = "";
  
  protected HTMLPanel            widget;
  protected CameraMoveWidget     cameraMove;
  protected CameraRotationWidget cameraRotation;
  protected boolean              mouseDown; // TODO remove the ones in the subclass...and keep these ones
  protected int                  mouseDownX;
  protected int                  mouseDownY;   
  protected boolean              isShiftDown;
  
  protected CameraControler      controller = new CameraControler();
  
  protected AbstractDashboard(){
    widget = new HTMLPanel(NOTHING);
    widget.setSize("100%", "100%");
    
    setGlobalCamera();
    setGlobalCameraRotation();
    
    initWidget(widget);
    
  }
  
  protected void addComponent(Widget component){
    widget.add(component);
  }
  
  protected CameraMoveWidget getGlobalCamera(){
    return cameraMove;
  }
  
  protected CameraRotationWidget getGlobalCameraRotation(){
    return cameraRotation;
  }
  
  protected CameraControler getController(){
    return controller;
  } 
  
  protected void removeComponent(Widget component){
    widget.remove(component);
  }
  
  private void setGlobalCamera(){
    // set up global camera
    cameraMove = new CameraMoveWidget();
    cameraMove.setVisible(false);//useless  
  }
  
  private void setGlobalCameraRotation(){
    cameraRotation = new CameraRotationWidget();
    cameraRotation.setVisible(false);//useless    
  }

  @Override
  public void onKeyPress(KeyPressEvent event) {
  }
  

  @Override
  public void onMouseUp(MouseUpEvent event) {
    mouseDown = false;   
  }

  @Override
  public void onMouseWheel(MouseWheelEvent event) {
    getController().doMouseWheel(event.getDeltaY());
  }

  @Override
  public void onMouseMove(MouseMoveEvent event) {   
    if(isShiftDown && mouseDown){
      int diffX   = event.getX() - mouseDownX;
      int diffY   = event.getY() - mouseDownY;
      mouseDownX  = event.getX();
      mouseDownY  = event.getY();

      getController().incrementRotationX(diffY);
      getController().incrementRotationY(diffX);
    }    
  }

  @Override
  public void onMouseOut(MouseOutEvent event) {
    mouseDown = false; 
  }

  @Override
  public void onMouseDown(MouseDownEvent event) {
    mouseDown   = true;
    mouseDownX  = event.getX();
    mouseDownY  = event.getY();     
  }

  @Override
  public void onFocus(FocusEvent event) {
    DOM.setStyleAttribute(getWidget().getElement(), "border", "none");    
  }

  @Override
  public void onKeyUp(KeyUpEvent event) {
    switch( event.getNativeKeyCode()) {
      case 16: isShiftDown = false; break;
    }
  }

  @Override
  public void onKeyDown(KeyDownEvent event) {
    switch( event.getNativeKeyCode()) {
      case 16: isShiftDown = true; break;
    }     
  } 
    
}
