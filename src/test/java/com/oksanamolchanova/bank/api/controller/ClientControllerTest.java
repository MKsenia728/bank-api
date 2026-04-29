package com.oksanamolchanova.bank.api.controller;

import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.ClientService;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@DisplayName("ClientController test class")
class ClientControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ClientService service;

    @Autowired
    MockMvc mockMvc;

    private String balance;

    private String currency;

    @DisplayName("Positive test. Status 200, JSON response. Controller for find clients by balance more than and currency")
    @Test
    void getListClientsWithBalanceMoreThanTest() throws Exception {
        balance = "11000";
        currency = "EUR";
        List<ClientWithBalanceDto> clientsDto = new ArrayList<>();
        clientsDto.add(DtoCreator.getClientWithBalanceDto());

        Mockito.when(service.getListClientsWithBalanceMoreThan(balance, currency)).thenReturn(clientsDto);

        mockMvc.perform(
                        get("/clients/balance_more/" + balance + "/" + currency)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value(clientsDto.get(0).lastName()));
        Mockito.verify(service).getListClientsWithBalanceMoreThan(balance, currency);
    }

    @DisplayName("Negative test. Clients are not exist. Controller for find clients by balance more than and currency")
    @Test
    void getListClientsWithBalanceMoreThanNotExistTest() throws Exception {
        balance = "110000";
        currency = "EUR";
        Mockito.when(service.getListClientsWithBalanceMoreThan(balance, currency)).thenThrow(new DataNotFoundException(ErrorMessage.CLIENTS_NOT_FOUND));

        mockMvc.perform(
                        get("/clients/balance_more/" + balance + "/" + currency)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.CLIENTS_NOT_FOUND));
        Mockito.verify(service).getListClientsWithBalanceMoreThan(balance, currency);
    }

    @DisplayName("Negative test. Invalid balance. Controller for find clients by balance more than and currency")
    @Test
    void getListClientsWithBalanceMoreThanInvalidBalanceTest() throws Exception {
        balance = "balance";
        currency = "EUR";

        mockMvc.perform(
                        get("/clients/balance_more/" + balance + "/" + currency)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Value must be positive"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. Invalid currency type. Controller for find clients by balance more than and currency")
    @Test
    void getListClientsWithBalanceMoreThanInvalidCurrencyTest() throws Exception {
        String balance = "5000";
        String currency = "dollar";

        mockMvc.perform(
                        get("/clients/balance_more/" + balance + "/" + currency)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Invalid currency type entered"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }
}