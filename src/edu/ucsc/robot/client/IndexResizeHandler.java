package edu.ucsc.robot.client;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

import edu.ucsc.robot.util.Util;

public class IndexResizeHandler implements ResizeHandler {
	private MainScreen index;
	public IndexResizeHandler(MainScreen index){
		this.index = index;
	}

	@Override
	public void onResize(ResizeEvent event) {
        final int w = index.canvas.getOffsetWidth();
        final int h = index.canvas.getOffsetHeight();
        index.resize(w, h);
        Util.rightTop(index.dialog);
	}

}
