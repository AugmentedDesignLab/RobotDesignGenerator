package edu.ucsc.robot.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class WheelPanel extends SimplePanel{
	private static WheelPanel instance;
	
	private WheelPanel() {
		DisclosurePanel dPanel = new DisclosurePanel("Wheel Styles");
		FlexTable content = setupContent();		
		dPanel.setContent(content);
		add(dPanel);
	}

	private FlexTable setupContent() {
		FlexTable content = new FlexTable();
		content.setTitle("Click on a wheel to choose");
		content.setWidget(1, 0, new Button("Wheel #1"));
		content.setWidget(1, 1, new Button("Wheel #2"));
		content.setWidget(1, 2, new Button("Wheel #3"));
		content.setWidget(2, 0, new Button("Wheel #4"));
		content.setWidget(2, 1, new Button("Wheel #5"));
		return content;
	}
	
	public static WheelPanel getInstance() {
		if(instance == null)
			instance = new WheelPanel();
		return instance;
	}
}
