package edu.ucsc.robot.client.handler;

import com.akjava.gwt.three.client.gwt.core.CameraControler;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.Dashboard;
import edu.ucsc.robot.client.WorkplanePanel;

public class WorkplaneRefreshHandler extends AbstractHandler<Dashboard>
		implements AnimationCallback {

	private final WorkplanePanel workplane;
	public boolean leftArrowKeyDown = false;
	public boolean rightArrowKeyDown = false;

	public WorkplaneRefreshHandler(Dashboard index, WorkplanePanel workplane) {
		super(index);
		this.workplane = workplane;
		handle();
	}

	@Override
	public void handle() {
		AnimationScheduler scheduler = AnimationScheduler.get();
		scheduler.requestAnimationFrame(this);
		CameraControler controller = getIndex().getController();
		if(leftArrowKeyDown){
			workplane.theta -= 1;
		}
		if(rightArrowKeyDown){
			workplane.theta += 1;
		}
		workplane.updateCameraPosition();

		workplane.getRootComponent().setRotation(
				controller.getRadiantRotationX(),
				controller.getRadiantRotationY(),
				controller.getRadiantRotationZ());

		getIndex().getWebGlRenderer().render(workplane.getScene(),
				workplane.getCamera());

	}

	@Override
	public void execute(double timestamp) {
		handle();
	}

}
