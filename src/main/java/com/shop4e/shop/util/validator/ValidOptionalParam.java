package com.shop4e.shop.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OptionalParamValidator.class)
public @interface ValidOptionalParam {
  String message() default "Invalid optional parameter";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
