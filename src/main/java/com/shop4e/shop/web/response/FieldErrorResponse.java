package com.shop4e.shop.web.response;

public class FieldErrorResponse {
  private String fieldName;
  private String message;

  public FieldErrorResponse(String fieldName, String message) {
//    this.fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    this.fieldName = fieldName;
    this.message = message;
  }

  public FieldErrorResponse(String message) {
    this.message = message;
  }

  public String getFieldName() {
    return fieldName;
  }

  public FieldErrorResponse setFieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public FieldErrorResponse setMessage(String message) {
    this.message = message;
    return this;
  }

//  @Override
//  public String toString() {
//    return (fieldName != null) ? String.format("{\"%s\": \"%s\"}", fieldName, message) : String.format("{\"%s\"}", message);
//  }
}
