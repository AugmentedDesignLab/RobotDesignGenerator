package edu.ucsc.robot.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.ToolboxPanel;

public class ToolboxInputHandler extends AbstractHandler<ToolboxPanel> {

  public ToolboxInputHandler(ToolboxPanel index) {
    super(index);
    handle();
  }

  @Override
  public void handle() {
    getIndex().addLabelClickHandler(new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        getIndex().showPanels();
      }
     }
    );
    
    getIndex().addButtonClickHandler(new ClickHandler(){

      @Override
      public void onClick(ClickEvent event) {
        getIndex().hidePanels();
      }
      }
    );
    
  }

}
