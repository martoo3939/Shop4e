package com.shop4e.shop.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.http.HttpStatus;

public class GenericResponse<T> {

  @JsonProperty("body")
  private T body;
  @JsonProperty("status")
  private Integer status;
  @JsonProperty("responseAt")
  private LocalDateTime responseAt;

  public GenericResponse(T body, HttpStatus status) {
    this.body = body;
    this.status = status.value();
    this.responseAt = LocalDateTime.now(ZoneOffset.UTC);
  }

  public T getBody() {
    return body;
  }

  public GenericResponse<T> setBody(T body) {
    this.body = body;
    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public GenericResponse<T> setStatus(Integer status) {
    this.status = status;
    return this;
  }

  public LocalDateTime getResponseAt() {
    return responseAt;
  }

  public GenericResponse<T> setResponseAt(LocalDateTime responseAt) {
    this.responseAt = responseAt;
    return this;
  }
}
