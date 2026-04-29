package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Enum Currency Type Validator test")
class EnumCurrencyTypeConstraintTest {

    private final EnumCurrencyTypeConstraint enumCurrencyTypeConstraint = new EnumCurrencyTypeConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - is ENUM")
    @Test
    void isValidTest() {
        String currency = "USD";
        assertTrue(enumCurrencyTypeConstraint.isValid(currency, context));
    }

    @DisplayName("Negative test - is NULL")
    @Test
    void isNullValidTest() {
        String currency = null;
        assertTrue(enumCurrencyTypeConstraint.isValid(currency, context));
    }

    @DisplayName("Negative test - is not ENUM")
    @Test
    void isUnValidTest() {
        String currency = "Dollar";
        assertFalse(enumCurrencyTypeConstraint.isValid(currency, context));
    }
}