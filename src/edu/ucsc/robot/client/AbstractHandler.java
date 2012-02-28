package edu.ucsc.robot.client;

/**
 * this class acts as a controller....
 * It was added to give hints to where the next refactoring should
 * start....
 * @author huascarsanchez
 *
 * @param <T>
 */
public abstract class AbstractHandler <T> {
  private final T index;
  protected AbstractHandler(T index){
    this.index = index;
  }
  
  public abstract void handle();
  
  public T getIndex(){
    return index;
  }
}
