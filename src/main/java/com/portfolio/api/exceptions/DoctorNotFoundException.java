package com.portfolio.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DoctorNotFoundException extends RuntimeException {

  public DoctorNotFoundException() {
    super();
  }

  public DoctorNotFoundException(String message) {
    super(message);
  }
}
