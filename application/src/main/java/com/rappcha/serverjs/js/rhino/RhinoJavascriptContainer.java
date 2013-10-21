package com.rappcha.serverjs.js.rhino;

import com.google.common.io.Resources;

import com.rappcha.serverjs.js.JavascriptContainer;
import com.rappcha.serverjs.js.util.SynchronousReadyListener;
import com.rappcha.serverjs.model.RenderedPage;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;


public class RhinoJavascriptContainer implements JavascriptContainer {

  Logger LOG = LoggerFactory.getLogger(RhinoJavascriptContainer.class);

  private Context cx;
  private Global globalScope;
  private String containerId;
  private Server server;

  public RhinoJavascriptContainer(String containerId) {
    this.containerId = containerId;
    initialise();
  }

  private void initialise() {

    long start = System.currentTimeMillis();

    server = new Server(containerId);
    cx = ContextFactory.getGlobal().enterContext();

    try {
      cx.setOptimizationLevel(-1);
      cx.setLanguageVersion(Context.VERSION_1_8);

      globalScope = Main.getGlobal();
      if (!globalScope.isInitialized()) {
        globalScope.init(cx);
      }

      Object o = cx.getWrapFactory().wrapAsJavaObject(cx, globalScope, server, null);
      ScriptableObject.putProperty(globalScope, "server", o);

      run(cx, "/scripts/env.js");
      run(cx, "/scripts/rappcha.js");

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      LOG.info("Initialised Rhino container in " + (System.currentTimeMillis() - start) + "ms");
    }

  }

  @Override
  public RenderedPage setLocation(URL url) throws FileNotFoundException {

    LOG.info("[" + containerId + "] setLocation: " + url.toString());

    long start = System.currentTimeMillis();

    try {
      cx = Context.enter();
      cx.seal(containerId);

      SynchronousReadyListener readyListener = new SynchronousReadyListener();
      server.setListener(readyListener);

      run("window.location='" + url.toString() + "';");
      return readyListener.getRenderedPage();

    } catch (EvaluatorException e) {
      if (e.getMessage().contains("Can't find method java.io.FilterInputStream.read")) {
        throw new FileNotFoundException(url.toString());
      } else {
        throw e;
      }
    } finally {
      cx.unseal(containerId);
      LOG.info("[" + containerId + "] Got HTML in " + (System.currentTimeMillis() - start) + "ms");
    }

  }

  public void destroy() {
    Context.exit();
  }

  private void run(String script) {
    cx.evaluateString(globalScope, script, "<cmd>", 1, null);
  }

  private void run(Context cx, final String filename) throws IOException {
    String src = readString(filename);
    run(src);
  }

  private String readString(String filename) throws IOException {
    return Resources.toString(getClass().getResource(filename), Charset.forName("UTF-8"));
  }

}
