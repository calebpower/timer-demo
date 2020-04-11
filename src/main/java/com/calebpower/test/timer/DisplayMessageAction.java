package com.calebpower.test.timer;

/**
 * An action that displays a message.
 * 
 * @author LordInateur
 */
public class DisplayMessageAction implements TimerAction {

  @Override public void onAction(String message) {
    System.out.println("MESSAGE: " + message);
  }

}
