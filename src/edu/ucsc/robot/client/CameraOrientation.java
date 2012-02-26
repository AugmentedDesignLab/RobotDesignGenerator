/**
 * 
 */
package edu.ucsc.robot.client;

/**
 * A record class to capture the orientation of the camera.
 * @author Huascar A. Sanchez
 *
 */
public class CameraOrientation {
  private    int     cameraX; 
  private    int     cameraY; 
  private    int     cameraZ;
  
  /**
   * default constructor needed by GWT.
   */
  public CameraOrientation(){
    this(0, 0, 100);
  }
  
  /**
   * construct a camera orientation record.
   * @param cameraX x coordinate
   * @param cameraY y coordinate
   * @param cameraZ z coordinate
   */
  public CameraOrientation(int cameraX, int cameraY, int cameraZ){
    this.cameraX = cameraX;
    this.cameraY = cameraY;
    this.cameraZ = cameraZ;
  }
  
  public int getCameraX(){
    return cameraX;
  }
  
  public int getCameraY(){
    return cameraY;
  }
  
  public int getCameraZ(){
    return cameraZ;
  }

  public void setCameraX(int cameraX) {
    this.cameraX = cameraX;
  }

  public void setCameraY(int cameraY) {
    this.cameraY = cameraY;
  }

  public void setCameraZ(int cameraZ) {
    this.cameraZ = cameraZ;
  }
  
}
