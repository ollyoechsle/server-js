package com.rappcha.serverjs.model;

import java.util.Map;

/**
 * Represents a rendered page
 */
public class RenderedPage {

  /**
   * The HTML on the page as it is currently
   */
  private String html;

  /**
   * The model. This will be serialised into JSON and sent directly to the front end.
   */
  private Map model;

  /**
   * Constructor
   *
   * @param html  The pre rendered html
   * @param model The current model data
   */
  public RenderedPage(String html, Map model) {
    this.html = html;
    this.model = model;
  }

  public String getHtml() {
    return html;
  }

  public Map getModel() {
    return model;
  }
  
}
