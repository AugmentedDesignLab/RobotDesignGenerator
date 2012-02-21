/**
 * 
 */
package edu.ucsc.robot.client.board;

import com.akjava.gwt.three.client.THREE;
import com.akjava.gwt.three.client.renderers.WebGLRenderer;
import com.akjava.gwt.three.client.renderers.WebGLRenderer.WebGLCanvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Huascar A. Sanchez
 * 
 */
public abstract class BoardEntryPoint implements EntryPoint {

  private static final int TAB_HEIGHT = 30;
  
  private   WebGLRenderer   renderer;
  protected Timer           timer;
  protected WebGLCanvas     canvas;
  private   PopupPanel      dialog;
  private   Button          hideButton;
  private   VerticalPanel   main;
  protected int             canvasWidth;
  protected int             canvasHeight;
  protected TabLayoutPanel  tabPanel;
  protected PopupPanel      htmlDialog;

  /**
   * 
   * @param parent
   */
  public abstract void createPlayer(Panel parent);

  /**
   * @return a {@link WebGLCanvas} object.
   */
  public WebGLCanvas getCanvas() {
    return canvas;
  }

  /**
   * @return a human readable description of this player.
   */
  public String getHtml() {
    final StringBuilder htmlMessage = new StringBuilder("Powerd by ");
    htmlMessage.append("<a href='https://github.com/mrdoob/three.js/'>Three.js</a>").append(" & ")
        .append("<a href='http://code.google.com/intl/en/webtoolkit/'>GWT</a>");
    return htmlMessage.toString();
  }

  /**
   * 
   * @return a short title of this player.
   */
  public abstract String getPlayerTitle();

  /**
   * makes this player invisible to the user.
   */
  protected void hidePlayer() {
    main.setVisible(false);
    hideButton.setVisible(false);

  }

  /**
   * 
   * @param renderer
   * @param width
   * @param height
   */
  public void initialize(WebGLRenderer renderer, int width, int height) {
  }

  protected void leftBottom(PopupPanel dialog) {
    int h = Window.getClientHeight();
    int dh = dialog.getOffsetHeight();
    dialog.setPopupPosition(0, h - dh);
  }

  @Override
  public void onModuleLoad() {
    tabPanel = new TabLayoutPanel(TAB_HEIGHT, Unit.PX);

    RootLayoutPanel.get().add(tabPanel);

    int width  = Window.getClientWidth();
    int height = Window.getClientHeight() - TAB_HEIGHT;
    
    // PLEASE NOTE:
    // if com.google.gwt.core.client.JavaScriptException: (TypeError): Cannot read property
    //   'WebGLRenderer' of undefined
    // add lines and both js files on same directory with html <script type="text/javascript"
    // language="javascript" src="Three.js"></script> <script type="text/javascript"
    // language="javascript" src="stats.js"></script>
    renderer = THREE.WebGLRenderer();
    renderer.setSize(width, height);


    canvas = new WebGLCanvas(renderer);
    setupWebGLCanvas();

    tabPanel.add(canvas, getPlayerTitle());

    canvasWidth  = width;
    canvasHeight = height;
    initialize(renderer, width, height);

    timer = new Timer() {
      public void run() {
        update(renderer);
      }
    };

    if (!GWT.isScript()) {
      timer.scheduleRepeating(100);
    } else {
      timer.scheduleRepeating(1000 / 60);
    }

    dialog = new PopupPanel();
    
    setupDialogRoot();

    createPlayer(main);

    dialog.show();
    rightTop(dialog);

    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(ResizeEvent event) {
        final int w = canvas.getOffsetWidth();
        final int h = canvas.getOffsetHeight();
        canvasWidth = w;
        canvasHeight = h;
        resize(w, h);
        renderer.setSize(w, h);
        rightTop(dialog);
      }
    });
    
    
    
    final HTMLPanel html = new HTMLPanel(getHtml());
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

  /**
   * 
   */
  private void setupDialogRoot() {
    VerticalPanel dialogRoot = new VerticalPanel();
    dialogRoot.setSpacing(2);   
    final Label label = new Label("Control");
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
        rightTop(dialog);
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
  }

  /**
   * sets up the WebGLCanvas object by assiging the appropriate Mouse Listeners and Handlers.
   */
  private void setupWebGLCanvas() {
    canvas.setClearColorHex(0);

    canvas.addMouseUpHandler(new MouseUpHandler() {

      @Override
      public void onMouseUp(MouseUpEvent event) {

        BoardEntryPoint.this.onMouseUp(event);
      }
    });

    canvas.addMouseWheelHandler(new MouseWheelHandler() {
      @Override
      public void onMouseWheel(MouseWheelEvent event) {
        BoardEntryPoint.this.onMouseWheel(event);
      }
    });
    // hpanel.setFocus(true);

    canvas.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        BoardEntryPoint.this.onMouseClick(event);
      }
    });

    canvas.addMouseDownHandler(new MouseDownHandler() {

      @Override
      public void onMouseDown(MouseDownEvent event) {
        BoardEntryPoint.this.onMouseDown(event);

      }
    });

    canvas.addMouseOutHandler(new MouseOutHandler() {

      @Override
      public void onMouseOut(MouseOutEvent event) {
        BoardEntryPoint.this.onMouseOut(event);
      }
    });

    canvas.addMouseMoveHandler(new MouseMoveHandler() {

      @Override
      public void onMouseMove(MouseMoveEvent event) {
        BoardEntryPoint.this.onMouseMove(event);
      }
    });

    canvas.setWidth("100%");
    canvas.setHeight("100%");
  }

  /**
   * 
   * @param event
   */
  public void onMouseClick(ClickEvent event) {
  }

  /**
   * 
   * @param event
   */
  public void onMouseDown(MouseDownEvent event) {
  }

  /**
   * 
   * @param event
   */
  public void onMouseMove(MouseMoveEvent event) {
  }

  /**
   * 
   * @param event
   */
  public void onMouseOut(MouseOutEvent event) {
  }

  /**
   * 
   * @param event
   */
  public void onMouseUp(MouseUpEvent event) {
  }

  /**
   * 
   * @param event
   */
  public void onMouseWheel(MouseWheelEvent event) {
  }

  /**
   * 
   * @param width
   * @param height
   */
  public void resize(int width, int height) {
  }

  private void rightTop(PopupPanel dialog) {
    final int w = Window.getClientWidth();
    final int h = Window.getScrollTop();
    final int dw = dialog.getOffsetWidth();
    dialog.setPopupPosition(w - dw - 18, h);
  }

  /**
   * makes this player visible to the user.
   */
  protected void showPlayer() {
    main.setVisible(true);
    hideButton.setVisible(true);
    rightTop(dialog);
  }

  /**
   * 
   * @param renderer
   */
  public void update(WebGLRenderer renderer) {
  }

}
