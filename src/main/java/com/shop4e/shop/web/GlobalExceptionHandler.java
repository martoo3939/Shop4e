package com.shop4e.shop.web;

import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.response.ErrorResponse;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = CustomException.class)
  public ResponseEntity<?> handleException(CustomException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());

    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST, response);
  }

//  @ExceptionHandler(value = ConstraintViolationException.class)
//  public ResponseEntity<?> handleException(Exception ex) {
//    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST);
//  }

  @ExceptionHandler(value = SQLSyntaxErrorException.class)
  public ResponseEntity<?> handleException(SQLSyntaxErrorException ex) {
    return ResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Required request body is missing");

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
