package edu.ucsc.robot.util;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class Util {
  public static void rightTop(PopupPanel dialog, int width, int height){
    final int dw = dialog.getOffsetWidth();
    dialog.setPopupPosition(width - dw - 18, height);    
  }
  
	public static void rightTop(PopupPanel dialog) {
	  rightTop(dialog, Window.getClientWidth(), Window.getScrollTop());
	}

	public static void leftBottom(PopupPanel dialog) {
		int h = Window.getClientHeight();
		leftBottom(dialog, h);
	}
	
	 public static void leftBottom(PopupPanel dialog, int height) {
	    int dh = dialog.getOffsetHeight();
	    dialog.setPopupPosition(0, height - dh);
	  }

}
