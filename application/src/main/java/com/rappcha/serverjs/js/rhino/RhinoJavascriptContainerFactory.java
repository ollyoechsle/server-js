package com.rappcha.serverjs.js.rhino;

import com.rappcha.serverjs.js.JavascriptContainer;
import com.rappcha.serverjs.js.JavascriptContainerFactory;

public class RhinoJavascriptContainerFactory implements JavascriptContainerFactory {

  @Override
  public JavascriptContainer getInstance(String id) {
    return new RhinoJavascriptContainer(id);
  }

}
