package com.rappcha.serverjs.js.util;

import java.util.Map;

/**
 * Listener object for when the Javascript has completed its initial
 * execution and has stated that it is ready. The HTML and JSON it returns
 * can then be packaged up and sent to the front end.
 */
public interface ReadyListener {

  /**
   * Called when the javascript has run on the server
   * @param html The current HTML on the web page that should be sent to the front end
   * @param json The current state of the model which will also be sent directly to the front end
   */
  public void onDocumentReady(String html, Map json);

}
