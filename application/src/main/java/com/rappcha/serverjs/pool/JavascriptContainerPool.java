package com.rappcha.serverjs.pool;

import com.rappcha.serverjs.js.JavascriptContainer;

/**
 * Returns a javascript container. The intention is that there will be some kind of pool of
 * containers which will be reused.
 */
public interface JavascriptContainerPool {

  /**
   * Gets a Javascript container to service a request
   *
   * @return The container
   * @throws ContainerUnavailableException If there are no containers available
   */
  public JavascriptContainer getJavascriptContainer() throws ContainerUnavailableException;

  /**
   * Returns a container back to the pool where it can be reused
   *
   * @param container The container
   */
  public void finished(JavascriptContainer container);

}

