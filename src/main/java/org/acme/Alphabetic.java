package org.acme;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphabeticValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Alphabetic {

    String message() default "may only contain letters (A-Z, a-z)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
