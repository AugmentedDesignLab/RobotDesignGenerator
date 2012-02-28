package edu.ucsc.robot.util;

public class Messages {
  public static final String INFO = "<strong>0 - 9</strong>: colors, <strong>click</strong>: " 
      + "add voxel, <strong>shift + click</strong>: remove voxel, " 
      + "<strong>shift + drag</strong>: rotate ";
  
  private static final String DIV_OPEN = "<div "
      + "style='position:absolute; top:5px; width:100%'; text-align:center>"
      + "<span style='color: #444; background-color: #fff; border-bottom: 1px solid #ddd; " 
      + "padding: 8px 10px; text-transform: uppercase;'>";
  
  private static final String DIV_CLOSE = "</span></div>"; 
  
  public static final String HTML = DIV_OPEN 
      + INFO
      + DIV_CLOSE;
  
  public static final String HELP = DIV_OPEN + "Once you done designing, try these --> " + DIV_CLOSE;
  
  public static final String DIV_HTML = "<div></div>";
  
  private Messages(){}
  
  

}
