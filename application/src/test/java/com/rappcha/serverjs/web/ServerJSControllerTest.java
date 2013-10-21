package com.rappcha.serverjs.web;

import com.rappcha.serverjs.js.JavascriptContainer;
import com.rappcha.serverjs.model.RenderedPage;
import com.rappcha.serverjs.pool.ContainerUnavailableException;
import com.rappcha.serverjs.pool.JavascriptContainerPool;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerJSControllerTest {

  private ServerJSController controller;

  private JavascriptContainer container;

  @Test
  public void testPing() throws Exception {
    assertNotNull(controller.ping());
  }

  @Test
  public void normalFlow() throws Exception {
    when(container.setLocation(eq(new URL("http://localhost:8080/blog/"))))
        .thenReturn(new RenderedPage("Hello, World", new HashMap()));

    ModelAndView modelAndView = controller.doRequest("blog");
    assertEquals("Hello, World", modelAndView.getModel().get("html"));
    assertNotNull(modelAndView.getModel().get("model"));
  }

  @Test(expected = ApplicationNotFoundException.class)
  public void get404IfCannotConnect() throws Exception {
    when(container.setLocation(any(URL.class)))
        .thenThrow(new ConnectException());
    controller.doRequest("UNKNOWN");
  }

  /* End of tests */

  @Before
  public void setUp() throws ConnectException {
    controller = new ServerJSController(
        new MockContainerPool(),
        new StaticUrlResolver("http://localhost:8080/")
    );
    container = mock(JavascriptContainer.class);
  }

  private class MockContainerPool implements JavascriptContainerPool {

    @Override
    public JavascriptContainer getJavascriptContainer() throws ContainerUnavailableException {
      return container;
    }

    @Override
    public void finished(JavascriptContainer container) {
      // do nothing
    }
  }

}
