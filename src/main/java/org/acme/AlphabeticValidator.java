package org.acme;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphabeticValidator implements ConstraintValidator<Alphabetic, String> {

    private static final String REGEX = "[a-zA-Z]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true; // @NotNull sollte dies abklären
        }

        return value.matches(REGEX);
    }
}
