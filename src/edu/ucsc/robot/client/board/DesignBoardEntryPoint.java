/**
 * 
 */
package edu.ucsc.robot.client.board;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.cameras.Camera;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.akjava.gwt.three.client.scenes.Scene;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;

/**
 * @author Huascar A. Sanchez
 *
 */
public abstract class DesignBoardEntryPoint extends BoardEntryPoint {
  private static final int DEFAULT_ZOOM             = 10;
  private static final int MIN_CAMERA               = 5;

  
  protected Camera            camera;
  protected CameraOrientation orientation;
  protected int               screenWidth; 
  protected int               screenHeight;
  protected Scene             scene;
  protected boolean           mouseDown;  
  protected int               mouseDownX;
  protected int               mouseDownY;
  protected long              mouseLast;
  private   int               zoom;

  
  /**
   * invokes the WebGLRenderer before any update.
   * @param renderer current {@link WebGLRenderer} instance.
   */
  protected abstract void beforeUpdate(WebGLRenderer renderer);  
  
  private void createCamera(Scene scene, int width, int height){
    if(camera != null){ scene.remove(camera); }
    camera = THREE.PerspectiveCamera(35, (double)width/height, 1, 6000);
    scene.add(camera);
  }
  
  @Override public void initialize(WebGLRenderer renderer, int width, int height){
    orientation   = new CameraOrientation();
    screenWidth   = width;
    screenHeight  = height;
    
    renderer.setClearColorHex(0x333333, 1);
    
    scene = THREE.Scene();
    
    createCamera(scene, width, height);   
    initializeOthers(renderer);    
  }

  
  /**
   * initialize other visible components on this {@link DesignBoardEntryPoint design board}.
   * @param renderer current {@link WebGLRenderer} instance.
   */
  protected abstract  void initializeOthers(WebGLRenderer renderer) ;  
  
  @Override
  public void onMouseWheel(MouseWheelEvent event) {
    if(event.isShiftKeyDown()) { onMouseWheelWithShiftKey(event.getDeltaY()); }
    else {
      final long currentTime = System.currentTimeMillis();
      if(mouseLast + 100 > currentTime) { zoom *= 2;            }
      else                              { zoom  = DEFAULT_ZOOM; }
      
      int onZ = orientation.cameraZ + event.getDeltaY() * zoom;
      onZ     = Math.max(MIN_CAMERA, onZ);
      onZ     = Math.min(4000, onZ);
      
      orientation.cameraZ = onZ;
      mouseLast           = currentTime;
    }
  }
  
  /**
   * action handler on MouseWheel and Shift Key.
   * @param deltaY received input value.
   */
  public void onMouseWheelWithShiftKey(int deltaY){ 
  } 
  
  @Override
  public void onMouseDown(MouseDownEvent event) {
    mouseDown   = true;
    mouseDownX  = event.getX();
    mouseDownY  = event.getY();
  }

  @Override
  public void onMouseUp(MouseUpEvent event) {
    mouseDown = false;
  }
  
  @Override
  public void onMouseOut(MouseOutEvent event) {
    mouseDown = false;
  }  
  
  @Override public void resize(int width, int height){
    if(scene == null) return;
    
    screenWidth   = width;
    screenHeight  = height;
    createCamera(scene, width, height);    
  }
  
  @Override public void update(WebGLRenderer renderer){
    if(orientation == null) return;
    
    beforeUpdate(renderer);
    
    final int cameraX = orientation.cameraX;
    final int cameraY = orientation.cameraY;
    final int cameraZ = orientation.cameraZ;
    
    camera.getPosition().set(cameraX, cameraY, cameraZ);
    renderer.render(scene, camera);    
  }
  
  /**
   * A record class to capture the orientation of the camera.
   * @author Huascar A. Sanchez
   *
   */
  static class CameraOrientation {
    int     cameraX; 
    int     cameraY; 
    int     cameraZ = 100;
  }

}
