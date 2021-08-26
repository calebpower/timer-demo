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
    Timer timer = Timer.build();
    
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
      for(;;) {
        System.out.print("Gimme something: ");
        String something = keyboard.nextLine();
        System.out.print("How long? ");
        long seconds = 5L;
        try {
          seconds = Long.parseLong(keyboard.nextLine());
        } catch(NumberFormatException e) {
          System.out.println("You were dumb so we're going to make it 5 seconds.");
        }
        timer.queue(something, seconds);
      }
    }
  }

}
