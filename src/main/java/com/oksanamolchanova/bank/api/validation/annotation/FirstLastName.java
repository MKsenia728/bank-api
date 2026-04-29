package com.oksanamolchanova.bank.api.validation.annotation;

import com.oksanamolchanova.bank.api.validation.impl.NameConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NameConstraint.class})
public @interface FirstLastName {
    String message() default "The name must start with a capital letter," +
            " at least 2 characters, only Latin or Russian letters, " +
            "apostrophe characters and one \"-\" character are allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

