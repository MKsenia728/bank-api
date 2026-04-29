package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Positive decimal Validator test")
class PositiveDecimalConstraintTest {

    private final PositiveDecimalConstraint positiveDecimalConstraint = new PositiveDecimalConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive Validator test")
    @ParameterizedTest
    @ValueSource(strings = {
            "1000",
            "1000.55"
    })
    void isValidTest(String number) {
        assertTrue(positiveDecimalConstraint.isValid(number, context));
    }

    @DisplayName("Negative Validator test - number is NULL")
    @Test
    void isNullValidTest() {
        assertFalse(positiveDecimalConstraint.isValid(null, context));
    }

    @DisplayName("Negative Validator test")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "number",
            "10,0",
            "-10,0",
            "10.005555",
            "a10.0a"
    })
    void isInValidTest(String number) {
        assertFalse(positiveDecimalConstraint.isValid(number, context));
    }
}