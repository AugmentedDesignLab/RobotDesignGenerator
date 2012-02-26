/*******************************************************************************
 *
 * Copyright 2011 Spiffy UI Team   
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.cameras.Camera;
import com.akjava.gwt.three.client.core.Geometry;
import com.akjava.gwt.three.client.core.Object3D;
import com.akjava.gwt.three.client.core.Projector;
import com.akjava.gwt.three.client.lights.Light;
import com.akjava.gwt.three.client.objects.Mesh;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.akjava.gwt.three.client.renderers.WebGLRenderer.WebGLCanvas;
import com.akjava.gwt.three.client.scenes.Scene;
import com.akjava.gwt.html5.client.HTML5InputRange;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucsc.robot.util.Util;

/**
 * This class is the main entry point for our GWT module.
 */
public class MainScreen implements EntryPoint{
	public static final int MIN_CAMERA = 5;
	WebGLCanvas canvas;

	boolean mouseDown;
	int mouseDownX;
	int mouseDownY;
	long mouseLast;
	protected int screenWidth;
	protected int screenHeight;

	ZoomingRecord zooming = new ZoomingRecord();
	CameraOrientation orientation = new CameraOrientation();
	
	Scene scene = THREE.Scene();
	Camera camera;
	WebGLRenderer renderer = THREE.WebGLRenderer();
	Projector projector = THREE.Projector();

	Object3D rootGroup;
	Object3D backgroundContainer;
	Object3D selectedObject;
	private PopupPanel bottomPanel;
	PopupPanel dialog;
	private VerticalPanel main;
	private Button hideButton;
	protected PopupPanel htmlDialog;

	HTML5InputRange rotationRange;
	HTML5InputRange rotationYRange;
	HTML5InputRange rotationZRange;
	HTML5InputRange meshScaleX;
	HTML5InputRange meshScaleY;
	HTML5InputRange meshScaleZ;
	HTML5InputRange positionX;
	HTML5InputRange positionY;
	HTML5InputRange positionZ;

	/**
	 * The Index page constructor
	 */
	public MainScreen() {
	}

	@Override
	public void onModuleLoad() {
		screenWidth = Window.getClientWidth();
		screenHeight = Window.getClientHeight();

		setupRenderer();
		setupWebGLCanvas(renderer);
		RootLayoutPanel.get().add(canvas);
		setupScene();
		setupCamera();

		rootGroup = THREE.Object3D();
		scene.add(rootGroup);

		backgroundContainer = THREE.Object3D();
		rootGroup.add(backgroundContainer);

		final Geometry geo = THREE.PlaneGeometry(100, 100, 20, 20);
		final Mesh mesh = THREE.Mesh(geo,
				THREE.MeshBasicMaterial().color(0x666666).wireFrame(true)
						.build());
		mesh.setRotation(Math.toRadians(-90), 0, 0);
		backgroundContainer.add(mesh);

		setupUpdater();

		dialog = new PopupPanel();

		setupDialogRoot();
		createPlayer(main);

		dialog.show();
		Util.rightTop(dialog);

		Window.addResizeHandler(new IndexResizeHandler(this));

		HTMLPanel html = new HTMLPanel(getHtml());
		html.setWidth("100%");
		html.setHeight("20px");
		html.setStyleName("text");

		htmlDialog = new PopupPanel();
		htmlDialog.add(html);
		htmlDialog.setPopupPosition(150, 30);
		htmlDialog.setWidth("100%");
		htmlDialog.setStyleName("transparent");
		htmlDialog.show();

	}

	public String getHtml() {
		final StringBuilder htmlMessage = new StringBuilder("Powerd by ");
		htmlMessage
				.append("<a href='https://github.com/mrdoob/three.js/'>Three.js</a>")
				.append(" & ")
				.append("<a href='http://code.google.com/intl/en/webtoolkit/'>GWT</a>");
		return htmlMessage.toString();
	}

	public void resize(int width, int height) {
		if (scene == null)
			return;

		screenWidth = width;
		screenHeight = height;
		setupCamera();
		renderer.setSize(width, height);
		Util.leftBottom(bottomPanel);
	}

	private void setupDialogRoot() {
		VerticalPanel dialogRoot = new VerticalPanel();
		dialogRoot.setSpacing(2);
		Label label = new Label("Control");
		label.setStyleName("title");
		dialog.add(dialogRoot);
		dialogRoot.add(label);

		main = new VerticalPanel();
		main.setVisible(false);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setWidth("100%");
		hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		dialogRoot.add(hPanel);
		hideButton = new Button("Hide Control");

		hideButton.setVisible(false);
		hideButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				main.setVisible(false);
				hideButton.setVisible(false);
				Util.rightTop(dialog);
			}
		});

		hPanel.add(hideButton);
		dialogRoot.add(main);

		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showPlayer();
			}
		});
		
		dialogRoot.add(ChassisPanel.getInstance());
	}

	public void createPlayer(Panel parent) {
		final HorizontalPanel dataControls = new HorizontalPanel();
		parent.add(dataControls);
		rotationRange = new HTML5InputRange(-180, 180, 0);
		rotationYRange = new HTML5InputRange(-180, 180, 0);
		rotationZRange = new HTML5InputRange(-180, 180, 0);
		meshScaleX = new HTML5InputRange(0, 150, 3);
		meshScaleY = new HTML5InputRange(0, 150, 3);
		meshScaleZ = new HTML5InputRange(0, 150, 3);
		positionX = new HTML5InputRange(-150, 150, 0);
		positionY = new HTML5InputRange(-150, 150, 0);
		positionZ = new HTML5InputRange(-150, 150, 0);
		createBottomPanel();
		showPlayer();
	}

	private void createBottomPanel() {
		// TODO (whoever) implement the bottom panel containing generate,
		// print3D, and help....
		bottomPanel = new PopupPanel();
		bottomPanel.setVisible(true);
		bottomPanel.setSize("650px", "40px");
		final VerticalPanel main = new VerticalPanel();
		bottomPanel.add(main);
		bottomPanel.show();
		Util.leftBottom(bottomPanel);
	}

	protected void showPlayer() {
		main.setVisible(true);
		hideButton.setVisible(true);
		Util.rightTop(dialog);
	}

	private void setupUpdater() {
		IndexUpdater updater = new IndexUpdater(this);
		if (!GWT.isScript()) {
			updater.scheduleRepeating(100);
		} else {
			updater.scheduleRepeating(1000 / 60);
		}
	}

	private void setupScene() {
		orientation.setCameraY(10);
		zooming.update(5);

		scene.add(THREE.AmbientLight(0x888888));
		Light pointLight = THREE.PointLight(0xffffff);
		pointLight.setPosition(0, 10, 300);
		scene.add(pointLight);
	}

	private void setupRenderer() {
		renderer.setSize(screenWidth, screenHeight);
		renderer.setClearColorHex(0x333333, 1);
	}

	private void setupWebGLCanvas(WebGLRenderer renderer) {
		canvas = new WebGLCanvas(renderer);
		canvas.setClearColorHex(0xcccccc);
		IndexInputHandler handler = new IndexInputHandler(this);
		canvas.addMouseUpHandler(handler);
		canvas.addMouseWheelHandler(handler);
		canvas.addClickHandler(handler);
		canvas.addMouseDownHandler(handler);
		canvas.addMouseOutHandler(handler);
		canvas.addMouseMoveHandler(handler);
		canvas.setWidth("100%");
		canvas.setHeight("100%");
	}

	private void setupCamera() {
		if (camera != null && scene != null) {
			scene.remove(camera);
		}
		camera = THREE.PerspectiveCamera(35, (double) screenWidth
				/ screenHeight, 1, 6000);
		scene.add(camera);
	}
}
