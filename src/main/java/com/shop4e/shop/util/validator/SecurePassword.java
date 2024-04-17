package com.shop4e.shop.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecurePasswordValidator.class)
@Documented
public @interface SecurePassword {
  String message() default "Password must be between 8 and 100 characters long must contain at least 3 digits, one special character, one uppercase letter.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
