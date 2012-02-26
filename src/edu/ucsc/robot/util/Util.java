package edu.ucsc.robot.util;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class Util {
	public static void rightTop(PopupPanel dialog) {
		final int w = Window.getClientWidth();
		final int h = Window.getScrollTop();
		final int dw = dialog.getOffsetWidth();
		dialog.setPopupPosition(w - dw - 18, h);
	}

	public static void leftBottom(PopupPanel dialog) {
		int h = Window.getClientHeight();
		int dh = dialog.getOffsetHeight();
		dialog.setPopupPosition(0, h - dh);
	}

}
