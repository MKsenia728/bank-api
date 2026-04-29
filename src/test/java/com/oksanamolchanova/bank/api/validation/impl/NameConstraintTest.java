package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FirstName or lastName Validator test")
class NameConstraintTest {

    private final NameConstraint nameConstraint = new NameConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive Validator test")
    @ParameterizedTest
    @ValueSource(strings = {
            "Anna",
            "Anna-Mary",
            "Наталья",
            "Дем'ян"
    })
    void isValidTest(String name) {
        assertTrue(nameConstraint.isValid(name, context));
    }

    @DisplayName("Negative Validator test - name is NULL")
    @Test
    void isNullValidTest() {
        assertFalse(nameConstraint.isValid(null, context));
    }

    @DisplayName("Negative Validator test")
    @ParameterizedTest
    @ValueSource(strings = {
            "anna",
            "Anna35",
            "N",
            "",
            "Vik-тория",
            "An_na"
    })
    void isValid(String name) {
        assertFalse(nameConstraint.isValid(name, context));
    }
}