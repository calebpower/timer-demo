package com.calebpower.test.timer;
import java.util.Scanner;

/**
 * Driver for timer demo.
 * 
 * @author LordInateur
 */
public class Main {
  
  /**
   * Entry point.
   * 
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    Timer timer = Timer.build(new DisplayMessageAction(), 5);
    
    // catch CTRL + C
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        try {
          timer.stop();
          System.out.println("\nGoodbye!");
          Thread.sleep(200);
          System.exit(0);
        } catch(InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
    
    try(Scanner keyboard = new Scanner(System.in)) {
      for(;;)
        timer.queue(keyboard.nextLine());
    }
  }

}
