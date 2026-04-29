package com.oksanamolchanova.bank.api.dto;

import com.oksanamolchanova.bank.api.validation.annotation.PositiveInteger;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ManagerDto(
        @PositiveInteger
        String id,

        String firstName,

        String lastName,

        String status,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime updatedAt) {
}
