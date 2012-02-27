/**
 * 
 */
package edu.ucsc.robot.client;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This represents the Robot Design Generator Dashboard...
 * @author Huascar A. Sanchez
 *
 */
public class Dashboard extends AbstractDashboard implements EntryPoint {
  private int width;
  private int height;
  
  private final WebGLRenderer  render;
  private final WorkspacePanel workspace;
  private final ToolboxPanel   toolbox;
  
  /**
   * default constructor.
   */
  public Dashboard(){
    super();
    
    width  = Window.getClientWidth();
    height = Window.getClientHeight();     
    
    // add info panel 
    addComponent(new InfoPanel());
    
    // set up renderer (important)
    render = THREE.WebGLRenderer();
    render.setSize(width, height);
    
    // add workspace panel
    workspace = new WorkspacePanel(this);
    addComponent(workspace);
    
    //addComponent(new BottomPanel());
         
    toolbox = new ToolboxPanel(this);    
    toolbox.displayOnRightTopCorner();

  }
  
  public static Dashboard getInstance(){
    return Installer.INSTANCE;
  }
  
  @Override
  public void onModuleLoad() {
    final Dashboard dashboard = getInstance();
    RootPanel.get().add(dashboard);
  }
  
  public WebGLRenderer getRenderer(){
    return render;
  }
  
  public int getScreenWidth(){
    return width;
  }
  
  public int getScreenHeight(){
    return height;
  }
  
  public WorkspacePanel getWorkspacePanel(){
    return workspace;
  }
  
  public ToolboxPanel getToolbox(){
    return toolbox;
  }
  
  public void setDimensions(int width, int height){
    this.width  = width;
    this.height = height;
  }
  
  @Override
  public void onMouseDown(MouseDownEvent event) {
    super.onMouseDown(event);
    if(event.getNativeButton() == NativeEvent.BUTTON_MIDDLE){
      return;
    }
    
    final int x = event.getX();
    final int y = event.getY();
    
    workspace.selectObjectOnPlane(x, y);       
  }


  @Override
  public void onMouseMove(MouseMoveEvent event) {
    super.onMouseMove(event);
    
    if(event.getNativeButton() == NativeEvent.BUTTON_MIDDLE){
      return;
    }
    
    final int x = event.getX();
    final int y = event.getY();
    
    workspace.dragSelectedObject(x, y);    
  }
  

  @Override
  public void onMouseOut(MouseOutEvent event) {
    super.onMouseOut(event);
    workspace.unselectObjectOnPlane();
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
