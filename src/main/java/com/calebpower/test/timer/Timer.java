package com.calebpower.test.timer;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An implementation of a timer.
 * 
 * @author LordInateur
 */
public class Timer implements Runnable {
  
  private Thread thread = null;
  private TreeMap<Long, List<String>> entries = new TreeMap<>();
  
  private Timer() {
    this.entries = new TreeMap<>();
  }
  
  /**
   * Instantiates the timer.
   * 
   * @return the new Timer object
   */
  public static Timer build() {
    Timer timer = new Timer();
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
        synchronized(entries) {
          while(entries.size() > 0 && entries.firstKey() < System.currentTimeMillis()) {
            var entry = entries.remove(entries.firstKey());
            for(var message : entry)
              System.out.println(message);
          }
          entries.notifyAll();
        }
        Thread.sleep(500L);
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
   * @param delay the delay
   */
  public void queue(String message, long delay) {
    synchronized(entries) {
      long fireTime = System.currentTimeMillis() + delay * 1000;
      if(!entries.containsKey(fireTime))
        entries.put(fireTime, new ArrayList<>());
      var entryList = entries.get(fireTime);
      entryList.add(message);
      entries.notifyAll();
    }
    System.out.printf("QUEUED '%1$s' (wait %2$d seconds)\n", message, delay / 1000);
  }
}
