/**
 * 
 */
package edu.ucsc.robot.util;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Huascar A. Sanchez
 * 
 */
public class Console {
  private Console() {
    throw new AssertionError();
  }

  /**
   * logs a javascript object on the screen.
   * 
   * @param object a {@link JavaScriptObject} object.
   */
  public final native void log(JavaScriptObject object)/*-{
		console.log(object);
  }-*/;

  /**
   * logs a string object on the screen.
   * 
   * @param object
   */
  public static final native void log(String object)/*-{
		console.log(object);
  }-*/;

}
