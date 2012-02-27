package edu.ucsc.robot.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class WheelPanel extends FlexTable {
	private static WheelPanel instance;
	
	// TODO (ZP) probably you should use the DisclosurePanel: http://examples.roughian.com/index.htm#Panels~DisclosurePanel 
	private WheelPanel() {
		setText(0, 0, "Wheel Styles");
		FlexCellFormatter formatter = getFlexCellFormatter();
		formatter.setColSpan(0, 0, 3);
		formatter.setAlignment(0, 0, HasAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		setTitle("Click on a wheel to choose");
		setWidget(1, 0, new Button("Wheel #1"));
		setWidget(1, 1, new Button("Wheel #2"));
		setWidget(1, 2, new Button("Wheel #3"));
		setWidget(2, 0, new Button("Wheel #4"));
		setWidget(2, 1, new Button("Wheel #5"));		
	}
	
	public static WheelPanel getInstance() {
		if(instance == null)
			instance = new WheelPanel();
		return instance;
	}
}
