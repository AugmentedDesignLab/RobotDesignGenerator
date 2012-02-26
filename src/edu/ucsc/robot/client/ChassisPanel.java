package edu.ucsc.robot.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
 
public class ChassisPanel extends FlexTable {
	private static ChassisPanel instance;
	private ChassisPanel(){
		setText(0, 0, "Chassis Styles");
		FlexCellFormatter formatter = getFlexCellFormatter();
		formatter.setColSpan(0, 0, 3);
		formatter.setAlignment(0, 0, HasAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		setTitle("Click on a chassis to choose");
		setWidget(1, 0, new Button("Truck"));
		setWidget(1, 1, new Button("Ambulance"));
		setWidget(1, 2, new Button("School Bus"));
		setWidget(2, 0, new Button("Sedan"));
		setWidget(2, 1, new Button("Tank"));

	}
	
	public static ChassisPanel getInstance() {
		if(instance == null)
			instance = new ChassisPanel();
		return instance;
	}
}
