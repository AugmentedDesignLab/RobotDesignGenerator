package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.gwt.Object3DUtils;
import com.google.gwt.user.client.Timer;

public class IndexUpdater extends Timer {

	private MainScreen index;
	public IndexUpdater(MainScreen index){
		this.index = index;
	}
	@Override
	public void run() {
	    if(index.orientation == null) return;
	    
	    Object3DUtils.setVisibleAll(index.backgroundContainer, true);
        
	    if(index.rootGroup!=null){
	    	index.rootGroup.getRotation().set(
	        Math.toRadians(index.rotationRange.getValue()),
	        Math.toRadians(index.rotationYRange.getValue()),
	        Math.toRadians(index.rotationZRange.getValue())
	      );
	    }
	    
	    int cameraX = index.orientation.getCameraX();
	    int cameraY = index.orientation.getCameraY();
	    int cameraZ = index.orientation.getCameraZ();
	    
	    index.camera.getPosition().set(cameraX, cameraY, cameraZ);
	    index.renderer.render(index.scene, index.camera);    
	}

}
