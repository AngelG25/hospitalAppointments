package com.portfolio.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PatientNotFoundException extends RuntimeException {

  public PatientNotFoundException() {
    super();
  }

  public PatientNotFoundException(String message) {
    super(message);
  }
}
