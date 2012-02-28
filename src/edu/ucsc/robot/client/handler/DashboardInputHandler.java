package edu.ucsc.robot.client.handler;

import com.google.gwt.dom.client.NativeEvent;
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

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.Dashboard;

public class DashboardInputHandler extends AbstractHandler <Dashboard> implements KeyDownHandler, 
    KeyUpHandler, FocusHandler, KeyPressHandler, MouseDownHandler, MouseOutHandler, 
    MouseMoveHandler, MouseWheelHandler, MouseUpHandler {
  
  private boolean  mouseDown;
  private int      mouseDownX;
  private int      mouseDownY;   
  private boolean  isShiftDown; 

  public DashboardInputHandler(Dashboard index) {
    super(index);
    mouseDown   = false;
    mouseDownX  = -1;
    mouseDownY  = -1;  
    isShiftDown = false;
    
    handle();
    
    
  }

  @Override
  public void handle() {
    getIndex().getWorkspace().addKeyDownHandler(this);
    getIndex().getWorkspace().addKeyPressHandler(this);
    getIndex().getWorkspace().addKeyUpHandler(this);
    getIndex().getWorkspace().addFocusHandler(this);
    getIndex().getWorkspace().addMouseDownHandler(this);
    getIndex().getWorkspace().addMouseOutHandler(this);
    getIndex().getWorkspace().addMouseUpHandler(this);
    getIndex().getWorkspace().addMouseMoveHandler(this);
    getIndex().getWorkspace().addMouseWheelHandler(this);     
  }

  @Override
  public void onFocus(FocusEvent event) {
    DOM.setStyleAttribute(getIndex().getMainWidget().getElement(), "border", "none"); 
  }

  @Override
  public void onKeyDown(KeyDownEvent event) {
    switch( event.getNativeKeyCode()) {
      case 16: isShiftDown = true; break;
    }
  }

  @Override
  public void onKeyPress(KeyPressEvent event) {
  }

  @Override
  public void onKeyUp(KeyUpEvent event) {
    switch( event.getNativeKeyCode()) {
      case 16: isShiftDown = false; break;
    }
  }

  @Override
  public void onMouseDown(MouseDownEvent event) {
    mouseDown   = true;
    mouseDownX  = event.getX();
    mouseDownY  = event.getY(); 
    
    if(event.getNativeButton() == NativeEvent.BUTTON_MIDDLE){
      return;
    }
    
    final int x = event.getX();
    final int y = event.getY();
    
    // this is bad bad bad...but...after friday, I will fix it...
    getIndex().getWorkspace().getWorkplane().getObjectDraggringHandler().selectObjectOnPlane(x, y);       
  }

  @Override
  public void onMouseMove(MouseMoveEvent event) {
    if(isShiftDown && mouseDown){
      int diffX   = event.getX() - mouseDownX;
      int diffY   = event.getY() - mouseDownY;
      mouseDownX  = event.getX();
      mouseDownY  = event.getY();

      getIndex().getController().incrementRotationX(diffY);
      getIndex().getController().incrementRotationY(diffX);
            
    }
    
    if(event.getNativeButton() == NativeEvent.BUTTON_MIDDLE){
      return;
    }
    
    final int x = event.getX();
    final int y = event.getY();
    
    getIndex().getWorkspace().getWorkplane().getObjectDraggringHandler().dragSelectedObject(x, y);      
  }

  @Override
  public void onMouseOut(MouseOutEvent event) {
    mouseDown = false; 
    getIndex().getWorkspace().getWorkplane().getObjectDraggringHandler().unselectObject();
  }

  @Override
  public void onMouseUp(MouseUpEvent event) {
    mouseDown = false; 
  }

  @Override
  public void onMouseWheel(MouseWheelEvent event) {
    getIndex().getController().doMouseWheel(event.getDeltaY());
  }

}
