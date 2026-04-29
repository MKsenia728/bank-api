package com.oksanamolchanova.bank.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Enum Currency Type Validator test")
class IbanConstraintTest {

    private final IbanConstraint ibanConstraint = new IbanConstraint();

    ConstraintValidatorContext context;

    @DisplayName("Positive test - match IBAN")
    @Test
    void isValidTest() {
        String iban = "DE 12 333333 44444444444444";
        assertTrue(ibanConstraint.isValid(iban, context));
    }

    @DisplayName("Negative test - null IBAN")
    @Test
    void isNullValidTest() {
        String iban = null;
        assertFalse(ibanConstraint.isValid(iban, context));
    }

    @DisplayName("Negative test - not match IBAN")
    @ParameterizedTest
    @ValueSource(strings = {
            "00 12 333333 44444444444444",
            "12 333333 44444444444444",
            "DE 12 aaaaaa 44444444444444",
            "de 12 333333 44444444444444",
            "",
            "DE-12-333333-44444444444444"})
    void isInValidTest(String invalidIban) {
        assertFalse(ibanConstraint.isValid(invalidIban, context));
    }
}