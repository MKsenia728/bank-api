package com.oksanamolchanova.bank.api.dto;

import com.oksanamolchanova.bank.api.validation.annotation.FirstLastName;
import jakarta.validation.constraints.NotNull;

public record ManagerCreateDto(
        @NotNull
        @FirstLastName
        String firstName,

        @NotNull
        @FirstLastName
        String lastName) {
}
