package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.core.Intersect;
import com.akjava.gwt.three.client.core.Object3D;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;

public class IndexInputHandler implements MouseUpHandler, MouseWheelHandler,
		ClickHandler, MouseDownHandler, MouseOutHandler, MouseMoveHandler {
	private MainScreen index;

	public IndexInputHandler(MainScreen index) {
		this.index = index;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
	    if(index.mouseDown){
	        final int diffX = event.getX() - index.mouseDownX;
	        final int diffY = event.getY() - index.mouseDownY;
	        
	        index.mouseDownX  = event.getX();
	        index.mouseDownY  = event.getY();
	        
	        index.rotationRange.setValue(index.rotationRange.getValue() + diffY);
	        index.rotationYRange.setValue(index.rotationYRange.getValue() + diffX);
	      }
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
	    index.mouseDown = false;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		index.mouseDown = true;
		index.mouseDownX = event.getX();
		index.mouseDownY = event.getY();

	}

	@Override
	public void onClick(ClickEvent event) {
		final int x = event.getX();
		final int y = event.getY();
		final JsArray<Intersect> intersects = index.projector
				.gwtPickIntersects(x, y, index.screenWidth, index.screenHeight,
						index.camera, index.scene);

		// only select the first intersect
		select(intersects.get(0).getObject());

	}

	private void select(Object3D selected) {
		index.selectedObject = selected;
		index.meshScaleX
				.setValue((int) (index.selectedObject.getScale().getX() * 10));
		index.meshScaleY
				.setValue((int) (index.selectedObject.getScale().getY() * 10));
		index.meshScaleZ
				.setValue((int) (index.selectedObject.getScale().getZ() * 10));

		index.positionX.setValue((int) (index.selectedObject.getPosition()
				.getX() * 10));
		index.positionY.setValue((int) (index.selectedObject.getPosition()
				.getY() * 10));
		index.positionZ.setValue((int) (index.selectedObject.getPosition()
				.getZ() * 10));
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		if (event.isShiftKeyDown()) {
			onMouseWheelWithShiftKey(event.getDeltaY());
		} else {
			final long currentTime = System.currentTimeMillis();
			if (index.mouseLast + 100 > currentTime) {
				index.zooming.update(index.zooming.getZoomValue() * 2);
			} else {
				index.zooming.restart();
			}

			int onZ = index.orientation.getCameraZ() + event.getDeltaY()
					* index.zooming.getZoomValue();
			onZ = Math.max(MainScreen.MIN_CAMERA, onZ);
			onZ = Math.min(4000, onZ);

			index.orientation.setCameraZ(onZ);
			index.mouseLast = currentTime;
		}
	}

	/**
	 * action handler on MouseWheel and Shift Key.
	 * 
	 * @param deltaY
	 *            received input value.
	 */
	public void onMouseWheelWithShiftKey(int deltaY) {
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
	    index.mouseDown = false;
	}

}
