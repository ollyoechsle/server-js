package com.rappcha.serverjs.web;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Basic url resolver which takes an app id and sticks it onto the end of a static base url.
 */
public class StaticUrlResolver implements UrlResolver {

  private String baseUrl;

  public StaticUrlResolver(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public URL getUrlFor(String appId) throws MalformedURLException {
    // TODO: Set index.html/prod.html in the apache welcome file list
    return new URL(baseUrl + appId + "/");
  }

}
