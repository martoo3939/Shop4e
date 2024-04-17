package com.shop4e.shop.exception;

import com.shop4e.shop.web.response.FieldErrorResponse;
import java.util.List;

public class CustomException extends RuntimeException {
  public CustomException(String message) {
    super(message);
  }

  public CustomException(List<FieldErrorResponse> errors) {
    super(errors.toString());
  }
}
