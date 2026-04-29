package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Enum Account Type Validator test")
class EnumAccountTypeConstraintTest {

    private final EnumAccountTypeConstraint enumAccountTypeConstraint = new EnumAccountTypeConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - is ENUM")
    @Test
    void isValidTest() {
        String type = "CURRENT";
        assertTrue(enumAccountTypeConstraint.isValid(type, context));
    }

    @DisplayName("Positive test - is ENUM")
    @Test
    void isNullValidTest() {
        String type = null;
        assertTrue(enumAccountTypeConstraint.isValid(type, context));
    }

    @DisplayName("Negative test - is not ENUM")
    @Test
    void isUnValidTest() {
        String type = "CUrr45";
        assertFalse(enumAccountTypeConstraint.isValid(type, context));
    }
}