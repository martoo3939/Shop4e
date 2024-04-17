package com.shop4e.shop.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SecurePasswordValidator implements ConstraintValidator<SecurePassword, String> {

  private Pattern pattern;
  private Matcher matcher;
  private static final String SECURE_PASSWORD_PATTERN =
      "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z]).{8,100}$";

  @Override
  public void initialize(SecurePassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return validatePassword(value);
  }

  private boolean validatePassword(String password) {
    pattern = Pattern.compile(SECURE_PASSWORD_PATTERN);
    matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
