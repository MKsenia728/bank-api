package com.oksanamolchanova.bank.api.service.impl;

import com.oksanamolchanova.bank.api.dto.AccountAfterCreateUpdateDto;
import com.oksanamolchanova.bank.api.dto.AccountCreateDto;
import com.oksanamolchanova.bank.api.dto.AccountDto;
import com.oksanamolchanova.bank.api.dto.AccountNameDto;
import com.oksanamolchanova.bank.api.entity.Account;
import com.oksanamolchanova.bank.api.entity.Client;
import com.oksanamolchanova.bank.api.entity.enums.AccountStatus;
import com.oksanamolchanova.bank.api.mapper.AccountMapper;
import com.oksanamolchanova.bank.api.repository.AccountRepository;
import com.oksanamolchanova.bank.api.repository.ClientRepository;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Account service test class")
@ExtendWith(MockitoExtension.class)
class AccountServiceImpTest {

    @InjectMocks
    AccountServiceImpl service;

    @Mock
    AccountMapper accountMapper;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ClientRepository clientRepository;

    private final String status = "ACTIVE";

    private final Account account = EntityCreator.getAccountEntity();

    private final AccountDto accountDto = DtoCreator.getAccountDto();

    @Test
    @DisplayName("Positive test. Get account by Id")
    void getAccountByIdTest() {

        Mockito.when(accountRepository.findAccountById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.toDto(account)).thenReturn(accountDto);

        service.getAccountById(account.getId().toString());

        Mockito.verify(accountRepository).findAccountById(account.getId());
        Mockito.verify(accountMapper).toDto(account);
    }

    @Test
    @DisplayName("Negative test. There is no element. Get account by Id.")
    void getNotExistAccountByIdTest() {
        String id = EntityCreator.UUID;
        assertThrows(DataNotFoundException.class, () -> service.getAccountById(id));
    }

    @Test
    @DisplayName("Positive test. Get account by Name")
    void getAccountByNameTest() {

        Mockito.when(accountRepository.findAccountByName(account.getName())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.toDto(account)).thenReturn(accountDto);

        service.getAccountByName(account.getName());

        Mockito.verify(accountRepository).findAccountByName(account.getName());
        Mockito.verify(accountMapper).toDto(account);
    }

    @Test
    @DisplayName("Negative test. There is no element. Get account by name.")
    void getNotExistAccountByNameTest() {
        String name = EntityCreator.NAME;
        assertThrows(DataNotFoundException.class, () -> service.getAccountByName(name));
    }

    @Test
    @DisplayName("Positive test. Get all accounts")
    void getAllAccountsTest() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        List<AccountNameDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(DtoCreator.getAccountNameDto());

        Mockito.when(accountRepository.getAllBy()).thenReturn(accountList);
        Mockito.when(accountMapper.toListDtoName(accountList)).thenReturn(accountDtoList);

        service.getAllAccounts();
        Mockito.verify(accountRepository).getAllBy();
        Mockito.verify(accountMapper).toListDtoName(accountList);
    }

    @Test
    @DisplayName("Negative test. There are no any accounts")
    void getNotExistAllAccountsTest() {
        Mockito.when(accountRepository.getAllBy()).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> service.getAllAccounts());
    }

    @Test
    @DisplayName("Positive test. Get all accounts by status")
    void getAllAccountsByStatusTest() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(accountDto);

        Mockito.when(accountRepository.getAllByStatus(AccountStatus.valueOf(status))).thenReturn(accountList);
        Mockito.when(accountMapper.toListDto(accountList)).thenReturn(accountDtoList);

        service.getAllAccountsByStatus(status);

        Mockito.verify(accountRepository).getAllByStatus(AccountStatus.valueOf(status));
        Mockito.verify(accountMapper).toListDto(accountList);
    }

    @Test
    @DisplayName("Negative test. There are no any accounts by status")
    void getNotExistAllAccountsByStatusTest() {
        Mockito.when(accountRepository.getAllByStatus(AccountStatus.valueOf(status))).thenReturn(new ArrayList<>());
        assertThrows(DataNotFoundException.class, () -> service.getAllAccountsByStatus(status));
    }

    @Test
    @DisplayName("Positive test. Create new account for client (tax client)")
    void createNewAccountTest() {
        Client client = EntityCreator.getClientEntity();
        String taxCode = client.getTaxCode();
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();
        AccountAfterCreateUpdateDto expectAccountAfterCreateUpdateDto = DtoCreator.getAccountAfterCreateDto("PENDING");

        Mockito.when(clientRepository.findClientByTaxCode(taxCode)).thenReturn(client);
        Mockito.when(accountRepository.findAccountByName(accountCreateDto.name())).thenReturn(Optional.empty());
        Mockito.when(accountMapper.toEntity(accountCreateDto)).thenReturn(account);
        Mockito.when(accountMapper.toDtoAfterCreate(account)).thenReturn(expectAccountAfterCreateUpdateDto);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        service.createNewAccount(accountCreateDto, taxCode);

        Mockito.verify(clientRepository).findClientByTaxCode(taxCode);
        Mockito.verify(accountRepository).findAccountByName(accountCreateDto.name());
        Mockito.verify(accountMapper).toEntity(accountCreateDto);
        Mockito.verify(accountMapper).toDtoAfterCreate(account);
        Mockito.verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Negative test. Client (tax code) does not exist")
    void createNotFoundClientExceptionNewAccountTest() {
        String taxCode = "111";
        Mockito.when(clientRepository.findClientByTaxCode(taxCode)).thenReturn(null);
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();
        assertThrows(DataNotFoundException.class, () -> service.createNewAccount(accountCreateDto, taxCode));
    }

    @Test
    @DisplayName("Negative test. Such account already exist")
    void createAlreadyExistAccountExceptionNewAccountTest() {
        Client client = EntityCreator.getClientEntity();
        String taxCode = client.getTaxCode();
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();

        Mockito.when(clientRepository.findClientByTaxCode(taxCode)).thenReturn(client);
        Mockito.when(accountRepository.findAccountByName(accountCreateDto.name())).thenReturn(Optional.of(account));

        assertThrows(DataAlreadyExistException.class, () -> service.createNewAccount(accountCreateDto, taxCode));
    }


    @Test
    @DisplayName("Positive test. Block account by statusActive and product id")
    void blockAccountByProductIdAndStatusTest() {
        String productId = "1";
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        Mockito.when(accountRepository.getAllByStatus(AccountStatus.valueOf(status))).thenReturn(accountList);

        accountList.get(0).setStatus(AccountStatus.BLOCKED);
        List<Account> updatedList = new ArrayList<>(accountList);
        List<AccountAfterCreateUpdateDto> accountAfterCreateUpdateDtoList = new ArrayList<>();
        accountAfterCreateUpdateDtoList.add(DtoCreator.getAccountAfterCreateDto("BLOCKED"));

        Mockito.when(accountRepository.saveAll(updatedList)).thenReturn(updatedList);
        Mockito.when(accountMapper.toListAfterCreateDto(accountList)).thenReturn(accountAfterCreateUpdateDtoList);

        List<AccountAfterCreateUpdateDto> resultList = service.blockAccountByProductIdAndStatus(productId, status);

        Mockito.verify(accountRepository).getAllByStatus(AccountStatus.valueOf(status));
        Mockito.verify(accountRepository).saveAll(updatedList);
        Mockito.verify(accountMapper).toListAfterCreateDto(accountList);

        assertEquals("BLOCKED", resultList.get(0).status());
    }


    @Test
    @DisplayName("Negative test. There are no any account by statusActive and product Id")
    void blockAccountNotFoundExceptionAccountByProductIdAndStatusTest() {
        String productId = "2";
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        Mockito.when(accountRepository.getAllByStatus(AccountStatus.ACTIVE)).thenReturn(accountList);
        assertThrows(DataNotFoundException.class, () -> service.blockAccountByProductIdAndStatus(productId, status));
    }
}