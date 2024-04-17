package com.shop4e.shop.util.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.web.response.FieldErrorResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Component
public class ValidatorUtil {

  private static final Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

  private final ObjectMapper objectMapper;

  public ValidatorUtil(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void checkForErrors(Errors errors) {
    if (errors.hasErrors()) {
      List<ObjectError> allErrors = errors.getAllErrors();
      List<FieldErrorResponse> errorMessages = new ArrayList<>();
      for (ObjectError error : allErrors) {
        if (error instanceof FieldError) {
          errorMessages.add(
              new FieldErrorResponse(((FieldError) error).getField(), error.getDefaultMessage()));
        } else {
          errorMessages.add(new FieldErrorResponse(error.getDefaultMessage()));
        }
      }

      String message = "";
      try {
        message = objectMapper.writeValueAsString(errorMessages);
      } catch (Exception ex) {
        logger.error("Error occurred during JSON conversion.", ex);
      }

      throw new CustomException(message);
    }
  }
}
