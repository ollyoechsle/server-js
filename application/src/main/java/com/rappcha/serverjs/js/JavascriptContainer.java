package com.rappcha.serverjs.js;

import com.rappcha.serverjs.model.RenderedPage;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.URL;


public interface JavascriptContainer {

  /**
   * Sets the location to a given URL. When the app is ready the function will return. The idea is
   * that this function will run synchronously until the app is ready or a specified timeout has
   * elapsed.
   *
   * @param url The url to call
   * @return A pre rendered page
   * @throws ConnectException If it isn't possible to connect to the other server
   * @throws FileNotFoundException If the app on the remote server doesn't exist
   */
  RenderedPage setLocation(URL url) throws ConnectException, FileNotFoundException;

  /**
   * Destroys the container and cleans up
   */
  public void destroy();

}
