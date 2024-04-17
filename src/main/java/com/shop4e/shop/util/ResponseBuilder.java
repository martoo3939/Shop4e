package com.shop4e.shop.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

  public static ResponseEntity<?> buildResponse(HttpStatus status, Object body) {
    HashMap<String, Object> response = new HashMap<>();
    response.put("body", body);
    response.put("responseAt", LocalDateTime.now(ZoneOffset.UTC));
    response.put("status", status.value());

    return new ResponseEntity<>(response, status);
  }

  public static <B> GenericResponse<B> buildTypedResponse(HttpStatus status, B body) {
    GenericResponse<B> response = new GenericResponse<>(body, status);

    return response;
  }

  public static ResponseEntity<?> buildResponse(HttpStatus status) {
    HashMap<String, Object> response = new HashMap<>();
    response.put("responseAt", LocalDateTime.now(ZoneOffset.UTC));
    response.put("status", status.value());

    return new ResponseEntity<>(response, status);
  }
}
