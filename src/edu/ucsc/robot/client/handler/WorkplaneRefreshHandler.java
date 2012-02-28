package edu.ucsc.robot.client.handler;

import com.akjava.gwt.three.client.gwt.core.CameraControler;
import com.google.gwt.user.client.Timer;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.Dashboard;
import edu.ucsc.robot.client.WorkplanePanel;

public class WorkplaneRefreshHandler extends AbstractHandler<Dashboard> {

  private final WorkplanePanel workplane;

  public WorkplaneRefreshHandler(Dashboard index, WorkplanePanel workplane) {
    super(index);
    this.workplane = workplane;
    handle();
  }

  @Override
  public void handle() {
    new Timer(){

      @Override
      public void run() {
        final CameraControler controller = getIndex().getController();
        workplane.getCamera().setPosition(
            controller.getPositionX(), 
            controller.getPositionY(), 
            controller.getPositionZ()
        );
        
        workplane.getRootComponent().setRotation(
            controller.getRadiantRotationX(), 
            controller.getRadiantRotationY(), 
            controller.getRadiantRotationZ()
        );
        
        getIndex().getWebGlRenderer().render(workplane.getScene(), workplane.getCamera());
        
      }}.scheduleRepeating(1000/60);    
  }

}
