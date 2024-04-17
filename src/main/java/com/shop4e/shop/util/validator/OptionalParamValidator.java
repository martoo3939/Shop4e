package com.shop4e.shop.util.validator;

import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OptionalParamValidator implements ConstraintValidator<ValidOptionalParam, Optional<String>> {

  @Override
  public void initialize(ValidOptionalParam constraintAnnotation) {

  }

  @Override
  public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
    return value.isEmpty() || (value.isPresent() && validateValue(value.get()));
  }

  private boolean validateValue(String value) {
    return value != null && !value.isEmpty();
  }
}
