package com.rappcha.serverjs.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if the service is unable to find the app at a given location
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends Exception {

  public ApplicationNotFoundException(String appId, Exception cause) {
    super(appId, cause);
  }
}
