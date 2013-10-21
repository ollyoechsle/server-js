package com.rappcha.serverjs.pool;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if the service is unable to get hold of a javascript container
 * to service a request. 
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ContainerUnavailableException extends Exception {

  public ContainerUnavailableException() {
  }
}
