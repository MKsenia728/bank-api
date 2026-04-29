package com.oksanamolchanova.bank.api.controller;

import com.oksanamolchanova.bank.api.dto.AccountAfterCreateUpdateDto;
import com.oksanamolchanova.bank.api.dto.AccountCreateDto;
import com.oksanamolchanova.bank.api.dto.AccountDto;
import com.oksanamolchanova.bank.api.dto.AccountNameDto;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.AccountService;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@DisplayName("AccountController test class")
class AccountControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService service;

    @Autowired
    MockMvc mockMvc;

    private final String status = "ACTIVE";

    @DisplayName("Positive test. Status 200, JSON response. Controller for find account by ID")
    @Test
    public void getAccountByIdResponseTest() throws Exception {
        String id = EntityCreator.UUID;
        AccountDto expectAccount = DtoCreator.getAccountDto();
        Mockito.when(service.getAccountById(id)).thenReturn(expectAccount);

        mockMvc.perform(get("/accounts/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectAccount.id()))
                .andExpect(jsonPath("$.name").value(expectAccount.name()))
                .andExpect(jsonPath("$.type").value(expectAccount.type()))
                .andExpect(jsonPath("$.status").value(expectAccount.status()))
                .andExpect(jsonPath("$.balance").value(expectAccount.balance()))
                .andExpect(jsonPath("$.currencyCode").value(expectAccount.currencyCode()))
                .andExpect(jsonPath("$.clientFirstName").value(expectAccount.clientFirstName()))
                .andExpect(jsonPath("$.clientLastName").value(expectAccount.clientLastName()));
    }

    @DisplayName("Negative test.Invalid ID. Controller for find account by ID")
    @Test
    public void getAccountByIdInvalidIdExceptionTest() throws Exception {
        String uuidId = "1111";
        mockMvc.perform(get("/accounts/id/" + uuidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("It is not UUID format"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. ID not exist. Controller for find account by ID")
    @Test
    public void getAccountByIdNotExistIdExceptionTest() throws Exception {
        String id = EntityCreator.UUID;
        Mockito.when(service.getAccountById(id)).thenThrow(new DataNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID));
        mockMvc.perform(get("/accounts/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID));
    }

    @DisplayName("Positive test. Status 200, JSON response. Controller for find account by name")
    @Test
    public void getAccountByNameResponseTest() throws Exception {
        String name = EntityCreator.NAME;
        AccountDto expectAccount = DtoCreator.getAccountDto();
        Mockito.when(service.getAccountByName(name)).thenReturn(expectAccount);

        mockMvc.perform(get("/accounts/name/" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectAccount.id()))
                .andExpect(jsonPath("$.name").value(expectAccount.name()))
                .andExpect(jsonPath("$.type").value(expectAccount.type()))
                .andExpect(jsonPath("$.status").value(expectAccount.status()))
                .andExpect(jsonPath("$.balance").value(expectAccount.balance()))
                .andExpect(jsonPath("$.currencyCode").value(expectAccount.currencyCode()))
                .andExpect(jsonPath("$.clientFirstName").value(expectAccount.clientFirstName()))
                .andExpect(jsonPath("$.clientLastName").value(expectAccount.clientLastName()));
    }

    @DisplayName("Negative test.Invalid name. Controller for find account by name")
    @Test
    public void getAccountByNameInvalidNameExceptionTest() throws Exception {
        String name = "11 222 4444";
        mockMvc.perform(get("/accounts/name/" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Account name must be IBAN"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. Name not exist. Controller for find account by name")
    @Test
    public void getAccountByNameNotExistNameExceptionTest() throws Exception {
        String name = EntityCreator.NAME;
        Mockito.when(service.getAccountByName(name)).thenThrow(new DataNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_NAME));
        mockMvc.perform(get("/accounts/name/" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNT_NOT_FOUND_BY_NAME));
    }

    @DisplayName("Positive test. Status 200, JSON response. Controller for find all accounts")
    @Test
    void getAllAccountsResponseTest() throws Exception {
        List<AccountNameDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(DtoCreator.getAccountNameDto());

        Mockito.when(service.getAllAccounts()).thenReturn(accountDtoList);

        mockMvc.perform(
                        get("/accounts/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(accountDtoList.get(0).name()));
        Mockito.verify(service).getAllAccounts();
    }

    @DisplayName("Negative test. Accounts do not exist. Controller for find all accounts")
    @Test
    void getAllAccountsNotExistTest() throws Exception {
        Mockito.when(service.getAllAccounts()).thenThrow(new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND));
        mockMvc.perform(get("/accounts/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNTS_NOT_FOUND));
    }

    @DisplayName("Positive test. Status 200, JSON response. Controller for find all accounts by given status")
    @Test
    void getAllAccountsByStatusResponseTest() throws Exception {
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(DtoCreator.getAccountDto());

        Mockito.when(service.getAllAccountsByStatus(status)).thenReturn(accountDtoList);

        mockMvc.perform(
                        get("/accounts/all/" + status)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(accountDtoList.get(0).id()))
                .andExpect(jsonPath("$[0].name").value(accountDtoList.get(0).name()))
                .andExpect(jsonPath("$[0].type").value(accountDtoList.get(0).type()))
                .andExpect(jsonPath("$[0].status").value(accountDtoList.get(0).status()))
                .andExpect(jsonPath("$[0].status").value(status))
                .andExpect(jsonPath("$[0].balance").value(accountDtoList.get(0).balance()))
                .andExpect(jsonPath("$[0].currencyCode").value(accountDtoList.get(0).currencyCode()))
                .andExpect(jsonPath("$[0].clientFirstName").value(accountDtoList.get(0).clientFirstName()))
                .andExpect(jsonPath("$[0].clientLastName").value(accountDtoList.get(0).clientLastName()));
        Mockito.verify(service).getAllAccountsByStatus(status);
    }

    @DisplayName("Negative test. Invalid status. Controller for find all accounts by given status")
    @Test
    void getAllAccountsByInvalidStatusTest() throws Exception {
        String invalidStatus = "InvalidStatus";
        mockMvc.perform(get("/accounts/all/" + invalidStatus)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Invalid account status entered"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. There are no accounts by status. Controller for find all accounts by given status")
    @Test
    void getAllAccountsByStatusNotExistTest() throws Exception {
        Mockito.when(service.getAllAccountsByStatus(status)).thenThrow(new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS));
        mockMvc.perform(get("/accounts/all/" + status)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS));
    }


    @DisplayName("Positive test. JSON response. Controller for create account by given client tax")
    @Test
    void createNewAccountTest() throws Exception {
        String taxCode = "123123123123";
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();
        AccountAfterCreateUpdateDto accountAfterCreateUpdateDto = DtoCreator.getAccountAfterCreateDto("PENDING");
        Mockito.when(service.createNewAccount(accountCreateDto, taxCode)).thenReturn(accountAfterCreateUpdateDto);
        mockMvc.perform(post("/accounts/new/client_tax/" + taxCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(accountAfterCreateUpdateDto.name()))
                .andExpect(jsonPath("$.type").value(accountAfterCreateUpdateDto.type()))
                .andExpect(jsonPath("$.status").value(accountAfterCreateUpdateDto.status()))
                .andExpect(jsonPath("$.balance").value(accountAfterCreateUpdateDto.balance()))
                .andExpect(jsonPath("$.currencyCode").value(accountAfterCreateUpdateDto.currencyCode()));

        Mockito.verify(service).createNewAccount(accountCreateDto, taxCode);
    }

    @DisplayName("Negative test. Validation not null input data. Controller for create account by given client tax")
    @Test
    void createNewAccountNotEnoughDataTest() throws Exception {
        String taxCode = "123123123123";

        mockMvc.perform(post("/accounts/new/client_tax/" + taxCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DtoCreator.getAccountCreateDtoWithoutNecessaryData()))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Negative test. Invalid type input data. Controller for create account by given client tax")
    @Test
    void createNewAccountInvalidTypeDataTest() throws Exception {
        String taxCode = "123123123123";
        mockMvc.perform(post("/accounts/new/client_tax/" + taxCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DtoCreator.getAccountCreateDtoWithInvalidData()))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Negative test. Account already exist. Controller for create account by given client tax")
    @Test
    void createNewAccountAlreadyExistAccountTest() throws Exception {
        String taxCode = "123123123123";
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();
        Mockito.when(service.createNewAccount(accountCreateDto, taxCode)).thenThrow(new DataAlreadyExistException(ErrorMessage.ACCOUNT_ALREADY_EXISTS));
        mockMvc.perform(post("/accounts/new/client_tax/" + taxCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateDto))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("406"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNT_ALREADY_EXISTS));
    }

    @DisplayName("Positive test. Server status 200. Controller for update account by given account status and product id")
    @Test
    void blockAccountByProductIdAndStatusTest() throws Exception {
        String productId = "1";
        AccountAfterCreateUpdateDto accountAfterCreateUpdateDto = DtoCreator.getAccountAfterCreateDto("BLOCKED");
        List<AccountAfterCreateUpdateDto> resultListDto = new ArrayList<>();
        resultListDto.add(accountAfterCreateUpdateDto);
        Mockito.when(service.blockAccountByProductIdAndStatus(productId, status)).thenReturn(resultListDto);

        mockMvc.perform(put("/accounts/block_account/" + productId + "/" + status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultListDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("BLOCKED"));
    }

    @DisplayName("Negative test. Invalid product Id. Controller for update account by given account status and product id")
    @Test
    void blockAccountByProductIdAndStatusWithInvalidProductIdTest() throws Exception {
        String productId = "-123.4";
        mockMvc.perform(put("/accounts/block_account/" + productId + "/" + status)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Value must be integer and positive"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. Accounts do not found. Controller for update account by given account status and product id")
    @Test
    void blockAccountByProductIdAndStatusDataNotFoundTest() throws Exception {
        String productId = "1";
        Mockito.when(service.blockAccountByProductIdAndStatus(productId, status)).thenThrow(new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID));

        mockMvc.perform(put("/accounts/block_account/" + productId + "/" + status)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID));
    }
}