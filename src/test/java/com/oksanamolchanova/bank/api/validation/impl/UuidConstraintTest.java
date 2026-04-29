package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Uuid validation test class")

class UuidConstraintTest {

    private final UuidConstraint uuidConstraint = new UuidConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - is UUID")
    @Test
    void isValidTest() {
        String uuid = "5aef1d7f-d14f-4655-9399-25bf27b16588";
        assertTrue(uuidConstraint.isValid(uuid, context));
    }

    @DisplayName("Negative test - is null")
    @Test
    void isNullValidTest() {
        String uuid = null;
        assertFalse(uuidConstraint.isValid(uuid, context));
    }

    @DisplayName("Negative test - not UUID")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "11111111-2222-3333-9444-5555555555",
            "WWWWWWWW-2222-3333-9444-555555555555",
            "11111111*2222*3333*8444*555555555555",
            "11111111-2222-3333-A444-555555555555",
    })
    void isInValidTest(String uuid) {
        assertFalse(uuidConstraint.isValid(uuid, context));
    }
}