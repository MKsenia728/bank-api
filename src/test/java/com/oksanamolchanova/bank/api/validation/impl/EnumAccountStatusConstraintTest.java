package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("Enum Account Status Validator test")
class EnumAccountStatusConstraintTest {
    private final EnumAccountStatusConstraint enumAccountStatusConstraint = new EnumAccountStatusConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - is ENUM")
    @Test
    void isValidTest() {
        String status = "ACTIVE";
        assertTrue(enumAccountStatusConstraint.isValid(status, context));
    }

    @DisplayName("Positive test - is NULL")
    @Test
    void isNullValidTest() {
        String status = null;
        assertTrue(enumAccountStatusConstraint.isValid(status, context));
    }

    @DisplayName("Negative test - is not ENUM")
    @Test
    void isUnValidTest() {
        String status = "active";
        assertFalse(enumAccountStatusConstraint.isValid(status, context));
    }
}