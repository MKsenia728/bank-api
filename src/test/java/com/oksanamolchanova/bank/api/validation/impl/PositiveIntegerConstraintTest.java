package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Positive integer validator test class")
class PositiveIntegerConstraintTest {

    private final PositiveIntegerConstraint positiveIntegerConstraint = new PositiveIntegerConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - positive integer")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1112223334"})
    void isValid(String number) {
        assertTrue(positiveIntegerConstraint.isValid(number, context));
    }

    @DisplayName("Negative test - null")
    @Test
    void isNullValidTest() {
        assertFalse(positiveIntegerConstraint.isValid(null, context));
    }

    @DisplayName("Negative test - positive integer")
    @ParameterizedTest
    @ValueSource(strings = {
            "100.00",
            "-100",
            "dd",
            "",
            "111222333444555666777",
    })
    void isInValid(String number) {
        assertFalse(positiveIntegerConstraint.isValid(number, context));
    }
}