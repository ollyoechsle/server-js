package com.rappcha.serverjs.js.util;

import com.rappcha.serverjs.model.RenderedPage;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of ready listener including the ability to call
 * the getRenderedPage() function synchronously from another class.
 *
 * The maximum time you have to wait for a response is limited to 5 seconds.
 */
public class SynchronousReadyListener implements ReadyListener {

  private RenderedPage renderedPage;
  private CountDownLatch latch;

  public SynchronousReadyListener() {
    this.latch = new CountDownLatch(1);
  }

  public RenderedPage getRenderedPage() {
    try {
      latch.await(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return renderedPage;
  }

  @Override
  public void onDocumentReady(String html, Map json) {
    this.renderedPage = new RenderedPage(html, json);
    latch.countDown();
  }


}
