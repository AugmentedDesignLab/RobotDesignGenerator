package edu.ucsc.robot.client.handler;

import com.akjava.gwt.three.client.cameras.Camera;
import com.akjava.gwt.three.client.core.Intersect;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Vector3;
import com.akjava.gwt.three.client.gwt.GWTDragObjectControler;
import com.akjava.gwt.three.client.objects.Mesh;
import com.google.gwt.core.client.JsArray;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.WorkplanePanel;

public class ObjectDraggingHandler extends AbstractHandler <WorkplanePanel> {
  private final GWTDragObjectControler dragger;
  public ObjectDraggingHandler(WorkplanePanel index) {
    super(index);
    dragger = new GWTDragObjectControler(getIndex().getScene(), getIndex().getProjector());
    handle();
  }
  
  
  public void dragSelectedObject(int x, int y){
    if(dragger.isSelected()){
      final int     width   = getIndex().getWidth();
      final int     height  = getIndex().getHeight();  
      final Camera  camera  = getIndex().getCamera();
      
      getIndex().getScene().updateMatrixWorld(true);
      final Vector3 newPos = dragger.moveSelectionPosition(x, y, width, height, camera);
      dragger.getSelectedDraggablekObject().setPosition(newPos);  
    }
  }
  
  @Override
  public void handle() { 
    // does nothing....since the what we need does not need to 
    // be executed instantenously...
  }  
  
  JsArray<Intersect> locateObjectOnPlane(int x, int y){
    final int width                 = getIndex().getWidth();
    final int height                = getIndex().getHeight();
    final Camera            camera  = getIndex().getCamera();
    final JsArray<Object3D> meshs   = getIndex().getMesh();
    
    return getIndex().getProjector().gwtPickIntersects(
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
    final int     width   = getIndex().getWidth();
    final int     height  = getIndex().getHeight();  
    final Camera  camera  = getIndex().getCamera();
    
    if(intersects.length() > 0){
      dragger.selectObject(
          intersects.get(0).getObject(), 
          x, y, 
          width, height, 
          camera
      );
    }         
  }
  
  public void showXYZPlaneCoordinates(){
    final Mesh plane = getIndex().getPlane();
    final String rotation = new StringBuilder("x=")
          .append(plane.getRotation().getX())
          .append(" y=")
          .append(plane.getRotation().getY())
          .append(" z=")
          .append(plane.getRotation().getZ())
    .toString();
    System.out.println(rotation);    
  }
  

  public void unselectObject(){
    dragger.unselectObject();
  }

}
