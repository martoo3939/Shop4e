package com.shop4e.shop.web.response;

import java.time.LocalDateTime;

public class ErrorResponse {
  private String message;

  public String getMessage() {
    return message;
  }

  public ErrorResponse setMessage(String message) {
    this.message = message;
    return this;
  }
}
