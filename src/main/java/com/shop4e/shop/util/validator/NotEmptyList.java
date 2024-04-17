package com.shop4e.shop.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoEmptyListValidator.class)
public @interface NotEmptyList {
  String message() default "List cannot contain empty fields";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
