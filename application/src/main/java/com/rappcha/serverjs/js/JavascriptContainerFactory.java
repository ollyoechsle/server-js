package com.rappcha.serverjs.js;

/**
 * Provides new javascript containers
 */
public interface JavascriptContainerFactory {

  /**
   * Gets a new instance of a javascript container
   *
   * @return The new instance
   */
  public JavascriptContainer getInstance(String id);

}
