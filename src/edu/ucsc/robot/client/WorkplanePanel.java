package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.cameras.Camera;
import com.akjava.gwt.three.client.core.Intersect;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Projector;
import com.akjava.gwt.three.client.core.Vector3;
import com.akjava.gwt.three.client.gwt.GWTDragObjectControler;
import com.akjava.gwt.three.client.gwt.core.CameraControler;
import com.akjava.gwt.three.client.lights.Light;
import com.akjava.gwt.three.client.objects.Mesh;
import com.akjava.gwt.three.client.scenes.Scene;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * 
 * @author Huascar A. Sanchez
 *
 */
public class WorkplanePanel extends HTMLPanel{
  
  private final Dashboard              index;
  private final Scene                  scene;
  private final Camera                 camera;
  private final Object3D               rootComponent;
  private final Projector              projector;
  private final Mesh                   plane;
  private final GWTDragObjectControler dragger;

  
  private int   radious; 
  private int   theta; 
  private int   phi; 

  
  private JsArray<Object3D> meshs;
  private int               width;
  private int               height;
  
  public WorkplanePanel(Dashboard index){
    this(index, 500, 500);
  }
  
  @SuppressWarnings("unchecked")
  public WorkplanePanel(Dashboard index, int width, int height){
    super("");
    
    this.width  = width;
    this.height = height;
    
    setSize("100%", "100%");
    DOM.setStyleAttribute(getElement(), "textAlign", "center");
    DOM.setStyleAttribute(getElement(), "margin", "0px");   
    
    meshs = ((JsArray<Object3D>) JsArray.createArray()); // unchecked warning
    
    this.index            = index;
    this.radious          = 1600;
    this.theta            = 45;
    this.phi              = 60;


    
    this.scene            = THREE.Scene();
    this.projector        = THREE.Projector();
    this.camera           = THREE.PerspectiveCamera(40, (double) width/height, 1, 10000);
    
    camera.setPosition(
        radious * Math.sin( theta * Math.PI / 360 ) * Math.cos( phi * Math.PI / 360 ), 
        radious * Math.sin( phi * Math.PI / 360 ), 
        radious * Math.cos( theta * Math.PI / 360 ) * Math.cos( phi * Math.PI / 360 )
    ); 
    
    scene.add(camera);
    rootComponent = THREE.Object3D();
    scene.add(rootComponent); 
    
    
    plane = THREE.Mesh(THREE.PlaneGeometry(50, 50, 20, 20), THREE.MeshBasicMaterial().color(0x888888).wireFrame().build());
    

    final int    rotation        =   1;
    final double angleChangeOnX  =   81.0;
    final double angleChangeOnY  =   0;
    final double angleChangeOnZ  =   126.0;
    

    
    plane.setRotation(rotation * angleChangeOnX, angleChangeOnY / 2, angleChangeOnZ);
    plane.setPosition(0,-5,0);
    rootComponent.add(plane); 
    
    final Light ambientLight = THREE.AmbientLight(0x404040);
    scene.add(ambientLight);
    
    final Light directionalLight = THREE.DirectionalLight(0x808080, 1);
    directionalLight.setPosition(-1, 1, -0.75);
    directionalLight.getPosition().normalize();
    rootComponent.add(directionalLight);
    
    final Light pointLight = THREE.PointLight(0xffffff);
    pointLight.setPosition(1, 1, 0.75);
    pointLight.getPosition().normalize();
    rootComponent.add(pointLight);

    dragger = new GWTDragObjectControler(scene, projector);     
    
    getElement().appendChild(index.getRenderer().getDomElement());  
    
    refresh();
  }
  
  public void unselect(){
    dragger.unselectObject();
  }
  
  public JsArray<Intersect> locateObjectOnPlane(int x, int y){
    return projector.gwtPickIntersects(
        x, 
        y, 
        width, 
        height, 
        camera, 
        meshs
    );
  }
  
  public void selectObjectOnPlane(int x, int y){
    JsArray<Intersect> intersects = locateObjectOnPlane(x, y);
    if(intersects.length() > 0){
      dragger.selectObject(intersects.get(0).getObject(), x, y, width, height, camera);
    }         
  }
  
  public void dragSelectedObject(int x, int y){
    if(dragger.isSelected()){
      scene.updateMatrixWorld(true);
      final Vector3 newPos = dragger.moveSelectionPosition(x, y, width, height, camera);
      dragger.getSelectedDraggablekObject().setPosition(newPos);  
    }
  }

  
  private void refresh(){
    new Timer(){

      @Override
      public void run() {
        final CameraControler controller = index.getController();
        camera.setPosition(
            controller.getPositionX(), 
            controller.getPositionY(), 
            controller.getPositionZ()
        );
        
        rootComponent.setRotation(
            controller.getRadiantRotationX(), 
            controller.getRadiantRotationY(), 
            controller.getRadiantRotationZ()
        );
        
        index.getRenderer().render(scene, camera);
        
      }}.scheduleRepeating(1000/60);
  }
  
  public void printRotationAngle(){
    final String rotation = new StringBuilder("x=").append(plane.getRotation().getX()).append(" y=").append(plane.getRotation().getY()).append(" z=").append(plane.getRotation().getZ()).toString();
    System.out.println(rotation);
  }

  
  
}
