package com.oksanamolchanova.bank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Info about account")
public record AccountDto(
        @Schema(description = "Unique id, format UUID")
        String id,

        @Schema(description = "account name, format IBAN")
        String name,

        @Schema(description = "Client`s name, string, first letter should be uppercase, 2 letters and longer")
        String clientFirstName,

        @Schema(description = "Client`s name, string, first letter should be uppercase, 2 letters and longer")
        String clientLastName,

        @Schema(description = "CURRENT / DEPOSIT / CREDIT")
        String type,

        @Schema(description = "ACTIVE / PENDING / BLOCKED")
        String status,

        @Schema(description = "Can be 0 and more")
        String balance,

        String currencyCode,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime updatedAt) {

}
