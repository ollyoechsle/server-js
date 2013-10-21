package com.rappcha.serverjs.web;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Gets the full url for a given app
 */
public interface UrlResolver {

  URL getUrlFor(String appId) throws MalformedURLException;

}
