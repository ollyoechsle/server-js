package com.rappcha.serverjs.web;

import com.rappcha.serverjs.js.JavascriptContainer;
import com.rappcha.serverjs.model.RenderedPage;
import com.rappcha.serverjs.pool.ContainerUnavailableException;
import com.rappcha.serverjs.pool.JavascriptContainerPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.MalformedURLException;


@Controller
public class ServerJSController {

  Logger LOG = LoggerFactory.getLogger(ServerJSController.class);

  private JavascriptContainerPool containerPool;

  private UrlResolver urlResolver;

  @Autowired
  public ServerJSController(JavascriptContainerPool containerPool, UrlResolver urlResolver) {
    this.containerPool = containerPool;
    this.urlResolver = urlResolver;
  }

  @RequestMapping("/")
  public String ping() {
    LOG.info("ping");
    return "OK";
  }

  @RequestMapping("/{appId}")
  public ModelAndView doRequest(@PathVariable(value = "appId") String appId)
      throws ContainerUnavailableException, ApplicationNotFoundException {

    LOG.info("Starting request for app : `" + appId + "`");

    JavascriptContainer container = containerPool.getJavascriptContainer();
    // TODO: If there is no container then work as would apache

    try {

      RenderedPage page = container.setLocation(
          urlResolver.getUrlFor(appId)
      );

      LOG.info("Request complete for app `" + appId + "`");

      return new ModelAndView(
          "prerendered",
          new ModelMap()
              .addAttribute("html", page.getHtml())
              .addAttribute("model", page.getModel())
      );

    } catch (FileNotFoundException e) {
      LOG.error("App does not exist `" + appId + "`");
      throw new ApplicationNotFoundException(appId, e);
    } catch (ConnectException e) {
      LOG.error("Could not connect to app `" + appId + "`");
      throw new ApplicationNotFoundException(appId, e);
    } catch (MalformedURLException e) {
      LOG.error("Cannot build url for app `" + appId + "`", e);
      return null;
    } finally {
      containerPool.finished(container);
    }

  }

  // TODO: Apache should handle all other types of request

}
