/**
 * 
 */
package edu.ucsc.robot.client;

/**
 * A record class to capture the zooming in/out values of the camera.
 * @author Huascar A. Sanchez
 *
 */
public class ZoomingRecord {
  private static final int DEFAULT_ZOOM   = 10;

  private int value;
  public ZoomingRecord(){
    this(DEFAULT_ZOOM);
  }
  
  /**
   * construct a ZoomingRecord class.
   * @param value
   */
  public ZoomingRecord(int value){
    this.value = value;
  }
  
  public int getZoomValue(){
    return value;
  }
 
  public void restart(){
    update(DEFAULT_ZOOM);
  }
  
  public void update(int zoomValue){
    this.value = zoomValue;
  }
  
}
