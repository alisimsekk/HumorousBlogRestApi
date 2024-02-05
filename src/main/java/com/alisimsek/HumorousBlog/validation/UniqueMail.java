package com.alisimsek.HumorousBlog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueMailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMail {
    String message() default "{humorous.constraint.mail.notunique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
