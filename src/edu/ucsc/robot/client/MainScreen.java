/**
 * 
 */
package edu.ucsc.robot.client;

import java.util.HashMap;
import java.util.Map;


import com.akjava.gwt.html5.client.HTML5InputRange;
import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.core.Geometry;
import com.akjava.gwt.three.client.core.Intersect;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Projector;
import com.akjava.gwt.three.client.core.Vector3;
import com.akjava.gwt.three.client.gwt.Object3DUtils;
import com.akjava.gwt.three.client.lights.Light;
import com.akjava.gwt.three.client.objects.Mesh;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucsc.robot.client.board.DesignBoardEntryPoint;

/**
 * @author Huascar A. Sanchez
 *
 */
public class MainScreen extends DesignBoardEntryPoint {
  final Projector projector = THREE.Projector();
  
  private static MainScreen mainScreen;
  
  private HTML5InputRange meshScaleX;
  private HTML5InputRange meshScaleY;
  private HTML5InputRange meshScaleZ;
  private HTML5InputRange positionX;
  private HTML5InputRange positionY;
  private HTML5InputRange positionZ;

  
  private HTML5InputRange rotationRange;
  private HTML5InputRange rotationYRange;
  private HTML5InputRange rotationZRange; 
  
  

  private Object3D            rootGroup; 
  private Object3D            backgroundContainer;
  //private TabLayoutPanel      toolsPanel;
  private PopupPanel          bottomPanel;
  
  Object3D                    selectedObject;
  Map<String,Vector3>         boneSizeMap     = new HashMap<String,Vector3>();
  
  /**
   * @return the lazy-loaded and thread-safe MainScreen object.
   */
  public static MainScreen getInstance(){
    return mainScreen;
  }
  
  @Override public void onMouseClick(ClickEvent event){
    final int x = event.getX();
    final int y = event.getY();
    final JsArray<Intersect> intersects = projector.gwtPickIntersects(
        x, 
        y, 
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
      final int diffX = event.getX() - mouseDownX;
      final int diffY = event.getY() - mouseDownY;
      
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
    mainScreen = this;
    
    orientation.setCameraY(10);
    zooming.update(5);
    canvas.setClearColorHex(0xcccccc);  
    
    scene.add(THREE.AmbientLight(0x888888));
    Light pointLight = THREE.PointLight(0xffffff);
    pointLight.setPosition(0, 10, 300);
    scene.add(pointLight);
    
    rootGroup = THREE.Object3D();
    scene.add(rootGroup);    
    
    backgroundContainer = THREE.Object3D();
    rootGroup.add(backgroundContainer);  
    
    final Geometry geo  = THREE.PlaneGeometry(100, 100, 20, 20);
    final Mesh     mesh = THREE.Mesh(geo, THREE.MeshBasicMaterial().color(0x666666).wireFrame(true).build());
    mesh.setRotation(Math.toRadians(-90), 0, 0);
    backgroundContainer.add(mesh);    
    
    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
      @Override
      public void onSelection(SelectionEvent<Integer> event) {
        int selection=event.getSelectedItem();
        if(selection==0){
          showPlayer();
          htmlDialog.setVisible(true);
        } else {
        hidePlayer();
        htmlDialog.setVisible(false);
        }
      }
    });

  }

  
  @Override
  protected void beforeUpdate(WebGLRenderer renderer) {
    Object3DUtils.setVisibleAll(backgroundContainer, true);
        
    if(rootGroup!=null){
      rootGroup.getRotation().set(
        Math.toRadians(rotationRange.getValue()),
        Math.toRadians(rotationYRange.getValue()),
        Math.toRadians(rotationZRange.getValue())
      );
    }
    
  }
  
  @Override public void resize(int width, int height){
    super.resize(width, height);
    leftBottom(bottomPanel);
  }

  @Override
  public void createPlayer(Panel parent) {
    final HorizontalPanel dataControls = new HorizontalPanel();
    parent.add(dataControls);
    rotationRange  = new HTML5InputRange(-180,180,0);
    rotationYRange = new HTML5InputRange(-180,180,0);
    rotationZRange = new HTML5InputRange(-180,180,0);
    meshScaleX     = new HTML5InputRange(0,150,3);
    meshScaleY     = new HTML5InputRange(0,150,3);
    meshScaleZ     = new HTML5InputRange(0,150,3);
    positionX      = new HTML5InputRange(-150,150,0);
    positionY      = new HTML5InputRange(-150,150,0);
    positionZ      = new HTML5InputRange(-150,150,0);
    createBottomPanel();
    showPlayer();
  }
  
  private void createBottomPanel(){
    // TODO (whoever) implement the bottom panel containing generate, print3D, and help....
    bottomPanel = new PopupPanel();
    bottomPanel.setVisible(true);
    bottomPanel.setSize("650px", "40px");
    final VerticalPanel main = new VerticalPanel();
    bottomPanel.add(main);
    bottomPanel.show(); 
    super.leftBottom(bottomPanel);
  }

  @Override
  public String getPlayerTitle() {
    return "Robot Design Generator";
  }  
  

}
