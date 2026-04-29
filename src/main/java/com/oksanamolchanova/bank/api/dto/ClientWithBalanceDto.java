package com.oksanamolchanova.bank.api.dto;

import java.util.List;

public record ClientWithBalanceDto(
        String status,

        String taxCode,

        String firstName,

        String lastName,

        String email,

        String phone,

        List<AccountBalanceDto> balanceAndCurrency) {
}
