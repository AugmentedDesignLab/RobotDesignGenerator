package edu.ucsc.robot.client.handler;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.WorkspacePanel;

public class WorkspaceRequestResizeHandler extends AbstractHandler<WorkspacePanel> implements
    RequiresResize, ProvidesResize {

  public WorkspaceRequestResizeHandler(WorkspacePanel index) {
    super(index);
    handle();
  }

  @Override
  public void onResize() {
    if (getIndex().getWidget() instanceof RequiresResize) {
      final int width  = getIndex().getDashboard().getScreenWidth()  - 350;
      final int height = getIndex().getDashboard().getScreenHeight() - 400;
      getIndex().setSize(width + "px", height + "px");
      ((RequiresResize) getIndex().getWidget()).onResize();
    }
  }

  @Override
  public void handle() {
    onResize();
  }

}
