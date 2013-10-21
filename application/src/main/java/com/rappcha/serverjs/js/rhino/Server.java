package com.rappcha.serverjs.js.rhino;

import com.rappcha.serverjs.js.util.ReadyListener;

import org.mozilla.javascript.ScriptableObject;

/**
 * This object is injected into the Javascript runtime environment for each web request. When the
 * application has reached a suitable state, it can then call the ready() function to notify the
 * server that the response is ready to be pushed to the client.
 */
public class Server extends ScriptableObject {

  private String id;
  private ReadyListener listener;

  public Server(String id) {
    this.id = id;
  }

  public void setListener(ReadyListener listener) {
    this.listener = listener;
  }

  @Override
  public String getClassName() {
    return "Server";
  }

  public String getName() {
    return "Container " + id;
  }

  /**
   * Notify the server that the JS is ready to be sent to the front end The JS provides the HTML of
   * the web page in its current state and a JSON object which will be sent direct to the front end,
   * so that on the client side no duplicate AJAX calls are necessary.
   *
   * @param html The current HTML on the document
   */
  public void ready(String html) {
    if (listener != null) {
      listener.onDocumentReady(html, null);
    }
  }

}
