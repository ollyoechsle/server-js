package com.rappcha.serverjs.pool;

import com.rappcha.serverjs.js.rhino.RhinoJavascriptContainerFactory;

import static org.junit.Assert.assertNotNull;

public class BasicJavascriptContainerPoolTest {

  private JavascriptContainerPool pool;

  public void setUp() throws ContainerUnavailableException {
    pool = new BasicJavascriptContainerPool(new RhinoJavascriptContainerFactory(), 1);
    assertNotNull(pool.getJavascriptContainer());
  }

}
