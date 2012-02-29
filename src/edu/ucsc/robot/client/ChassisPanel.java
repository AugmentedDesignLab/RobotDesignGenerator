package edu.ucsc.robot.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
 
public class ChassisPanel extends SimplePanel {
	private static ChassisPanel instance;
	private ChassisPanel(){
		DisclosurePanel dPanel = new DisclosurePanel("Chassis Styles");
		FlexTable content = setupContent();
		dPanel.setContent(content);
		add(dPanel);
	}

	private FlexTable setupContent() {
		FlexTable content = new FlexTable();
		content.setTitle("Click on a chassis to choose");
		content.setWidget(1, 0, new Button("Truck"));
		content.setWidget(1, 1, new Button("Ambulance"));
		content.setWidget(1, 2, new Button("School Bus"));
		content.setWidget(2, 0, new Button("Sedan"));
		content.setWidget(2, 1, new Button("Tank"));
		return content;
	}
	
	public static ChassisPanel getInstance() {
		if(instance == null)
			instance = new ChassisPanel();
		return instance;
	}
}
