package com.rappcha.serverjs.js;

import com.rappcha.serverjs.js.rhino.RhinoJavascriptContainer;
import com.rappcha.serverjs.model.RenderedPage;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.WebInfConfiguration;
import org.mortbay.jetty.webapp.WebXmlConfiguration;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class JavascriptContainerTest {

  protected static final int JETTY_PORT = 8888;
  protected static final String APP_CONTEXT = "/example-site";
  private JavascriptContainer container;

  @BeforeClass
  public static void startTestApplication() throws Exception {

    Server server = new Server(JETTY_PORT);
    WebAppContext app = new WebAppContext();
    app.setContextPath(APP_CONTEXT);
    app.setWar(System.getProperty("user.home")
               + "/projects/turbine-persistence/serverjs/example-site/src/main/webapp/");
    app.setConfigurationClasses(
        new String[]{
            WebInfConfiguration.class.getName(),
            WebXmlConfiguration.class.getName()
        }
    );
    app.setParentLoaderPriority(true);

    server.setHandler(app);
    server.start();

  }

  @Test
  public void prerenderedExampleSiteHtml() throws Exception {

    for (int i = 0; i < 3; i++) {

      RenderedPage renderedPage = getPage(APP_CONTEXT);

      assertNotNull(renderedPage.getHtml());

    }

  }

  @Test(expected = FileNotFoundException.class)
  public void notFound() throws Exception {
    RenderedPage renderedPage = getPage("/unknown-app");
  }

  /* End of Tests */

  private RenderedPage getPage(String appId)
      throws ConnectException, MalformedURLException, FileNotFoundException {
    return container.setLocation(new URL("http://localhost:" + JETTY_PORT + appId));
  }

  @Before
  public void setUp() {
    container = new RhinoJavascriptContainer("js1");
  }

  @After
  public void tearDown() {
    if (container != null) {
      container.destroy();
    }
  }

}
