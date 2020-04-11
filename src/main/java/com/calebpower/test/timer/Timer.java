package com.calebpower.test.timer;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/**
 * An implementation of a timer.
 * 
 * @author LordInateur
 */
public class Timer implements Runnable {
  
  private long delay = 0L;
  private Thread thread = null;
  private TimerAction action = null;
  private List<Entry<Long, String>> entries = null;
  
  private Timer(TimerAction action, long delay) {
    this.action = action;
    this.delay = delay;
    this.entries = new LinkedList<>();
  }
  
  /**
   * Instantiates the timer.
   * 
   * @param action the action to be taken
   * @param delay the delay in seconds
   * @return the new Timer object
   */
  public static Timer build(TimerAction action, long delay) {
    Timer timer = new Timer(action, delay * 1000);
    timer.thread = new Thread(timer);
    timer.thread.setDaemon(true);
    timer.thread.start();
    return timer;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public void run() {
    try {
      for(;;) {
        Entry<Long, String> entry = null;
        synchronized(entries) {
          while(entries.size() == 0) {
            entries.wait();
          }
          entry = entries.remove(0);
        }
        while(entry.getKey() > System.currentTimeMillis())
          Thread.sleep(500L);
        action.onAction(entry.getValue());
      }
    } catch(InterruptedException e) { }
  }
  
  /**
   * Stops the timer thread.
   */
  public void stop() {
    this.thread.interrupt();
  }
  
  /**
   * Queues a message to be dispatched later.
   * 
   * @param message the message
   */
  public void queue(String message) {
    synchronized(entries) {
      entries.add(new SimpleEntry<>(System.currentTimeMillis() + delay, message));
      entries.notifyAll();
    }
    System.out.printf("QUEUED '%1$s' (wait %2$d seconds)\n", message, delay / 1000);
  }
}
