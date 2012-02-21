/**
 * 
 */
package edu.ucsc.robot.client;

import java.util.HashMap;
import java.util.Map;

import com.akjava.gwt.bvh.client.BoxData;
import com.akjava.gwt.html5.client.HTML5InputRange;
import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.core.Geometry;
import com.akjava.gwt.three.client.core.Intersect;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Projector;
import com.akjava.gwt.three.client.core.Vector3;
import com.akjava.gwt.three.client.lights.Light;
import com.akjava.gwt.three.client.objects.Mesh;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.Messages.Select;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import edu.ucsc.robot.client.board.DesignBoardEntryPoint;

/**
 * @author Huascar A. Sanchez
 *
 */
public class MainScreen extends DesignBoardEntryPoint {
  private static final Projector PROJECTOR = THREE.Projector();
  
  private Label           loadingLabel      = new Label();
  private CheckBox        translatePosition;
  private HTML5InputRange positionYRange;
  private HTML5InputRange meshScaleX;
  private HTML5InputRange meshScaleY;
  private HTML5InputRange meshScaleZ;
  private HTML5InputRange positionX;
  private HTML5InputRange positionY;
  private HTML5InputRange positionZ;
  private PopupPanel      bottomPanel;
  protected boolean       playing;
  private HTML5InputRange positionXRange;
  private HTML5InputRange positionZRange;
  private CheckBox        drawBackground; 
  
  private HTML5InputRange rotationRange;
  private HTML5InputRange rotationYRange;
  private HTML5InputRange rotationZRange;  
  

  private Object3D            rootGroup; 
  private Object3D            boneContainer; 
  private Object3D            backgroundContainer;
  private Map<String,BoxData> boxDatas;  
  
  
  Object3D            selectedObject;
  Map<String,Vector3> boneSizeMap     = new HashMap<String,Vector3>();
  
  /**
   * @return the lazy-loaded and thread-safe MainScreen object.
   */
  public static MainScreen getInstance(){
    return Installer.MAIN_SCREEN;
  }
  
  @Override public void onMouseClick(ClickEvent event){
    final int x = event.getX();
    final int y = event.getY();
    final JsArray<Intersect> intersects = PROJECTOR.gwtPickIntersects(
        event.getX(), 
        event.getY(), 
        screenWidth, 
        screenHeight, 
        camera,
        scene
    );
    
    // only select the first intersect
    select(intersects.get(0).getObject()); 
    
  }

  @Override
  public void onMouseMove(MouseMoveEvent event) {
    if(mouseDown){
      final int diffX = event.getX()-mouseDownX;
      final int diffY = event.getY()-mouseDownY;
      
      mouseDownX  = event.getX();
      mouseDownY  = event.getY();
      
      rotationRange.setValue(rotationRange.getValue() + diffY);
      rotationYRange.setValue(rotationYRange.getValue() + diffX);
    }
  }
  
  private void select(Object3D selected){
    selectedObject = selected;
    meshScaleX.setValue((int) (selectedObject.getScale().getX()*10));
    meshScaleY.setValue((int) (selectedObject.getScale().getY()*10));
    meshScaleZ.setValue((int) (selectedObject.getScale().getZ()*10));
    
    positionX.setValue((int) (selectedObject.getPosition().getX()*10));
    positionY.setValue((int) (selectedObject.getPosition().getY()*10));
    positionZ.setValue((int) (selectedObject.getPosition().getZ()*10));
  } 
  
  
  @Override
  public void initializeOthers(WebGLRenderer renderer) {
    // TODO add workplane
  }

  
  @Override
  protected void beforeUpdate(WebGLRenderer renderer) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void createPlayer(Panel parent) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getPlayerTitle() {
    // TODO Auto-generated method stub
    return null;
  }  
  
  
  /**
   * Convenience class for creating a lazy loaded and thread safe singleton.
   * @author Huascar A. Sanchez
   *
   */
  static class Installer {
    static final MainScreen MAIN_SCREEN = new MainScreen();
  }

}
