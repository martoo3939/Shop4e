package com.shop4e.shop.util.validator;

import com.shop4e.shop.web.request.RegistrationRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(PasswordMatches constraintAnnotation) {
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    RegistrationRequest user = (RegistrationRequest) value;
    return user.getPassword().equals(user.getConfirmPassword());
  }
}
