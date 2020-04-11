package com.calebpower.test.timer;

/**
 * Some action to be taken.
 * 
 * @author LordInateur
 */
public interface TimerAction {
  
  /**
   * The action.
   * 
   * @param message the dispatched message
   */
  public void onAction(String message);
  
}
