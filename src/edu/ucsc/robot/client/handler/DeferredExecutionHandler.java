package edu.ucsc.robot.client.handler;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import edu.ucsc.robot.client.AbstractHandler;
import edu.ucsc.robot.client.Dashboard;

public class DeferredExecutionHandler extends AbstractHandler<Dashboard> {

  public DeferredExecutionHandler(Dashboard index) {
    super(index);
    handle();
  }

  @Override
  public void handle() {
    // Set focus on the widget. We have to use a deferred command or a
    // timer since GWT will lose it again if we set it in-line here
    Scheduler.get().scheduleDeferred(new ScheduledCommand(){
      @Override
      public void execute() {
        getIndex().getWorkspace().setFocus(true);
      }
     }
    ); 
  }

}
