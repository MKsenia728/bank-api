package com.oksanamolchanova.bank.api.validation.impl;

import com.oksanamolchanova.bank.api.validation.annotation.FirstLastName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class NameConstraint implements ConstraintValidator<FirstLastName, String> {

    private static final String NAME = "^(([А-Я][а-яё']{1,20})(-[А-Я][а-яё']{1,20})*|([A-Z][a-z`]{1,20})(-[A-Z][a-z`]{1,20})*)$";

    @Override
    public void initialize(FirstLastName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name != null) {
            return Optional.of(name)
                    .filter(s -> !s.isBlank())
                    .map(s -> s.matches(NAME))
                    .orElse(false);
        } else return false;
    }
}
