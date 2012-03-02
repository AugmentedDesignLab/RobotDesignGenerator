package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.cameras.Camera;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Projector;
import com.akjava.gwt.three.client.lights.Light;
import com.akjava.gwt.three.client.objects.Mesh;
import com.akjava.gwt.three.client.scenes.Scene;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.ucsc.robot.client.handler.ObjectDraggingHandler;
import edu.ucsc.robot.client.handler.WorkplaneRefreshHandler;

public class WorkplanePanel extends HTMLPanel {
	private final Scene scene;
	private final Camera camera;
	private final Object3D rootComponent;
	private final Projector projector;
	private final Mesh plane;

	private final ObjectDraggingHandler dragObjectHandler;

	private int radious;
	public int theta;
	public int phi;

	private JsArray<Object3D> meshs;
	private int width;
	private int height;
	public WorkplaneRefreshHandler refreshHandler;

	public WorkplanePanel(Dashboard index) {
		this(index, 500, 500);
	}

	@SuppressWarnings("unchecked")
	public WorkplanePanel(Dashboard index, int width, int height) {
		super("");

		this.width = width;
		this.height = height;

		setSize("100%", "100%");
		DOM.setStyleAttribute(getElement(), "textAlign", "center");
		DOM.setStyleAttribute(getElement(), "margin", "0px");

		meshs = ((JsArray<Object3D>) JsArray.createArray()); // unchecked
																// warning

		this.radious = 100;
		this.theta = 0;
		this.phi = 0;

		this.scene = THREE.Scene();
		this.projector = THREE.Projector();
		this.camera = THREE.PerspectiveCamera(40, (double) width / height, 1,
				10000);

		setupCamera();

		rootComponent = THREE.Object3D();
		scene.add(rootComponent);

		plane = THREE.Mesh(THREE.PlaneGeometry(50, 50, 20, 20), THREE
				.MeshBasicMaterial().color(0x888888).wireFrame().build());

		final int rotation = 1;
		final double angleChangeOnX = 81.0;
		final double angleChangeOnY = 0;
		final double angleChangeOnZ = 126.0;

		plane.setRotation(rotation * angleChangeOnX, angleChangeOnY / 2,
				angleChangeOnZ);
		plane.setPosition(0, -5, 0);
		rootComponent.add(plane);

		setupLights();

		dragObjectHandler = new ObjectDraggingHandler(this);

		getElement().appendChild(index.getWebGlRenderer().getDomElement());

		refreshHandler = new WorkplaneRefreshHandler(index, this);
	}

	public Camera getCamera() {
		return camera;
	}

	public int getHeight() {
		return height;
	}

	public JsArray<Object3D> getMesh() {
		return meshs;
	}

	public ObjectDraggingHandler getObjectDraggringHandler() {
		return dragObjectHandler;
	}

	public Mesh getPlane() {
		return plane;
	}

	public Projector getProjector() {
		return projector;
	}

	public Object3D getRootComponent() {
		return rootComponent;
	}

	public Scene getScene() {
		return scene;
	}

	public int getWidth() {
		return width;
	}

	private void setupCamera() {
		camera.setTarget(0, 0, 0);
		scene.add(camera);
	}

	public void updateCameraPosition() {
		phi = phi % 360;
		camera.setPosition(
				radious * Math.sin(theta * Math.PI / 360)
						* Math.cos(phi * Math.PI / 360),
				radious * Math.sin(phi * Math.PI / 360),
				radious * Math.cos(theta * Math.PI / 360)
						* Math.cos(phi * Math.PI / 360));

//		camera.getPosition().setY(camera.getPosition().getY() + 200);
	}

	/**
	 * set up ambient, directional, and point lights
	 */
	private void setupLights() {
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
	}
}
