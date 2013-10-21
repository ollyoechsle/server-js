package com.rappcha.serverjs.pool;

import com.rappcha.serverjs.js.JavascriptContainer;
import com.rappcha.serverjs.js.JavascriptContainerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Manages a pool of javascript containers
 */
public class BasicJavascriptContainerPool implements JavascriptContainerPool {

  Logger LOG = LoggerFactory.getLogger(BasicJavascriptContainerPool.class);

  private ArrayBlockingQueue<JavascriptContainer> queue;

  @Autowired
  public BasicJavascriptContainerPool(JavascriptContainerFactory factory, int size) {
    LOG.info("Initialising container pool (" + size + ")");
    this.queue = new ArrayBlockingQueue<JavascriptContainer>(size);
    for (int i = 0; i < size; i++) {
      boolean added = this.queue.offer(factory.getInstance("js" + i));
    }
  }

  /**
   * Gets the head of the container queue; if there is nothing available in the queue, it will wait
   * until one becomes available. If it has to wait longer than 10 seconds, then it will throw a
   * container unavailable exception
   *
   * @return The container when available.
   * @throws ContainerUnavailableException If a container cannot be retrieved within 10 seconds.
   */
  @Override
  public JavascriptContainer getJavascriptContainer() throws ContainerUnavailableException {
    LOG.info("Getting javascript container..");
    try {
      return this.queue.poll(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOG.info("Could not get javascript container within time limit");
      throw new ContainerUnavailableException();
    }
  }

  @Override
  public void finished(JavascriptContainer container) {
    LOG.info("Returning javascript container");
    boolean added = this.queue.offer(container);
  }

}
