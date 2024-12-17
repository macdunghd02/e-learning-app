package com.mdd.ela.util.validation;


import jakarta.validation.Constraint;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface EmailPattern {
    String message() default "invalidEmail";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
