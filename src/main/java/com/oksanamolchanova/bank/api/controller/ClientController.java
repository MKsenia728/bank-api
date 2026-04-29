package com.oksanamolchanova.bank.api.controller;

import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;
import com.oksanamolchanova.bank.api.service.interf.ClientService;
import com.oksanamolchanova.bank.api.validation.annotation.EnumCurrencyType;
import com.oksanamolchanova.bank.api.validation.annotation.PositiveDecimal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Controller for work with clients")
public class ClientController {
    public final ClientService clientService;

    @GetMapping("/balance_more/{balance}/{currency}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Request for find all clients with balance more than given value",
            description = "If accounts exists - returns all accounts with balance more than given value. " +
                    "If accounts do not exist - returns exception")
    @ApiResponse(responseCode = "200", description = "Successfully returned list if clients", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClientWithBalanceDto.class)))
    })
    public List<ClientWithBalanceDto> getListClientsWithBalanceMoreThanInCurrency(
            @PositiveDecimal
            @Parameter(description = "Balance should be 0 or more, format 0 or 0.00")
            @PathVariable("balance") String balance,
            @EnumCurrencyType
            @Parameter(description = "Check allowed currency")
            @PathVariable("currency") String currency) {
        return clientService.getListClientsWithBalanceMoreThan(balance, currency);
    }
}
