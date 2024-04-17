package com.shop4e.shop.web;

import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.response.ErrorResponse;
import java.sql.SQLSyntaxErrorException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = CustomException.class)
  public ResponseEntity<?> handleException(CustomException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());

    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST, response);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<?> handleException(Exception ex) {
    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = SQLSyntaxErrorException.class)
  public ResponseEntity<?> handleException(SQLSyntaxErrorException ex) {
    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST);
  }
}
