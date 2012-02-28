package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.gwt.core.CameraControler;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.akjava.gwt.three.client.ui.CameraMoveWidget;
import com.akjava.gwt.three.client.ui.CameraRotationWidget;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.ucsc.robot.client.handler.DashboardInputHandler;
import edu.ucsc.robot.client.handler.DeferredExecutionHandler;

public class Dashboard extends Composite {

  private static final String NOTHING = "";

  private HTMLPanel widget;
  private CameraMoveWidget cameraMove;
  private CameraRotationWidget cameraRotation;
  private CameraControler controller;

  private int width;
  private int height;

  private final WebGLRenderer render;
  private final WorkspacePanel workspace;
  private final ToolboxPanel toolbox;

  public Dashboard() {
    controller = new CameraControler();
    width = Window.getClientWidth();
    height = Window.getClientHeight();

    widget = new HTMLPanel(NOTHING);
    widget.setSize("100%", "100%");

    setGlobalCamera();
    setGlobalCameraRotation();

    initWidget(widget);

    // add info panel
    addComponent(new InfoPanel());

    // set up renderer (important)
    render = THREE.WebGLRenderer();
    render.setSize(width, height);

    // add workspace panel
    workspace = new WorkspacePanel(this);
    addComponent(workspace);
    new DashboardInputHandler(this);

    addComponent(new BottomPanel());

    toolbox = new ToolboxPanel(this);
    toolbox.displayOnRightTopCorner();

    new DeferredExecutionHandler(this);
  }
  
  
  public static Dashboard getInstance(){
    return Installer.INSTANCE;
  }

  protected void addComponent(Widget component) {
    widget.add(component);
  }

  public CameraControler getController() {
    return controller;
  }

  public CameraMoveWidget getGlobalCamera() {
    return cameraMove;
  }

  public CameraRotationWidget getGlobalCameraRotation() {
    return cameraRotation;
  }

  public Widget getMainWidget() {
    return getWidget();
  }

  public int getScreenHeight() {
    return height;
  }

  public int getScreenWidth() {
    return width;
  }

  public ToolboxPanel getToolbox() {
    return toolbox;
  }

  public WebGLRenderer getWebGlRenderer() {
    return render;
  }

  public WorkspacePanel getWorkspace() {
    return workspace;
  }

  public void setDimensions(int width, int height) {
    this.width = width;
    this.height = height;
  }

  private void setGlobalCamera() {
    // set up global camera
    cameraMove = new CameraMoveWidget();
    cameraMove.setVisible(false);// useless
  }

  private void setGlobalCameraRotation() {
    cameraRotation = new CameraRotationWidget();
    cameraRotation.setVisible(false);// useless
  }
  
  /**
   * A trick that give us a lazy loaded and thread safe Dashboard singleton...
   * @author Huascar A. Sanchez
   *
   */
  static class Installer {
    static final Dashboard INSTANCE = new Dashboard();
  }

}
