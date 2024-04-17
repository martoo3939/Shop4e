package com.shop4e.shop.util.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoEmptyListValidator implements ConstraintValidator<NotEmptyList, List<String>> {

  @Override
  public void initialize(NotEmptyList constraintAnnotation) {

  }

  @Override
  public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    return values.stream().allMatch(value -> value != null && !value.trim().isEmpty());
  }
}
