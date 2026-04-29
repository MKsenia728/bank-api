package com.oksanamolchanova.bank.api.dto;

import com.oksanamolchanova.bank.api.validation.annotation.Iban;

public record AccountNameDto(@Iban String name) {
}
