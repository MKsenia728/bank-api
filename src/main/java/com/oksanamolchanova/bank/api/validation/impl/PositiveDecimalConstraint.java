package com.oksanamolchanova.bank.api.validation.impl;

import com.oksanamolchanova.bank.api.validation.annotation.PositiveDecimal;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class PositiveDecimalConstraint implements ConstraintValidator<PositiveDecimal, String> {

    private static final String POSITIVE_DECIMAL_PATTERN = "\\d{0,15}(\\.\\d{0,2})?";
    @Override
    public void initialize(PositiveDecimal constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(value)
                .filter(s -> !s.isBlank())
                .map(s -> s.matches(POSITIVE_DECIMAL_PATTERN))
                .orElse(false);
    }
}
