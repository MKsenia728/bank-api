package com.oksanamolchanova.bank.api.service.impl;

import com.oksanamolchanova.bank.api.dto.AccountAfterCreateUpdateDto;
import com.oksanamolchanova.bank.api.dto.AccountCreateDto;
import com.oksanamolchanova.bank.api.dto.AccountDto;
import com.oksanamolchanova.bank.api.dto.AccountNameDto;
import com.oksanamolchanova.bank.api.entity.Account;
import com.oksanamolchanova.bank.api.entity.Client;
import com.oksanamolchanova.bank.api.entity.enums.AccountStatus;
import com.oksanamolchanova.bank.api.entity.enums.AccountType;
import com.oksanamolchanova.bank.api.mapper.AccountMapper;
import com.oksanamolchanova.bank.api.repository.AccountRepository;

import com.oksanamolchanova.bank.api.repository.ClientRepository;
import com.oksanamolchanova.bank.api.service.exceptions.*;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(String id) {
        log.info("Get account by id {}", id);
        return accountMapper.toDto(accountRepository.findAccountById(UUID.fromString(id)).orElseThrow(
                () -> {
                    log.warn(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID);
                    throw new DataNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID);
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountByName(String name) {
        log.info("Get account by name {}", name);
        return accountMapper.toDto(accountRepository.findAccountByName(name).orElseThrow(
                () -> {
                    log.warn(ErrorMessage.ACCOUNT_NOT_FOUND_BY_NAME);
                    throw new DataNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_NAME);
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountNameDto> getAllAccounts() {
        List<Account> accountList = accountRepository.getAllBy();
        log.info("Get name all accounts");
        if (accountList == null) {
            log.warn(ErrorMessage.ACCOUNTS_NOT_FOUND);
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND);
        }
        return new ArrayList<>(accountMapper.toListDtoName(accountList));
    }

    @Override
    @Transactional
    public List<AccountDto> getAllAccountsByStatus(String status) {
        List<Account> accountList = accountRepository.getAllByStatus(AccountStatus.valueOf(status));
        log.info("Get all accounts by status {}", status);
        if (accountList.size() == 0) {
            log.warn(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS);
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS);
        }
        return new ArrayList<>(accountMapper.toListDto(accountList));
    }

    @Override
    @Transactional
    public AccountAfterCreateUpdateDto createNewAccount(AccountCreateDto accountCreateDto, String clientTaxCode) {
        log.info("Create new account for client with tax code {}", clientTaxCode);
        Client client = clientRepository.findClientByTaxCode(clientTaxCode);
        AccountAfterCreateUpdateDto accountAfterCreateUpdateDto;
        Account account;

        if (client == null) {
            log.error(ErrorMessage.CLIENT_NOT_FOUND_BY_TAX_CODE);
            throw new DataNotFoundException(ErrorMessage.CLIENT_NOT_FOUND_BY_TAX_CODE);
        } else if (accountRepository.findAccountByName(accountCreateDto.name()).isPresent()) {
            log.error(ErrorMessage.ACCOUNT_ALREADY_EXISTS);
            throw new DataAlreadyExistException(ErrorMessage.ACCOUNT_ALREADY_EXISTS);
        }

        account = fillAccount(accountCreateDto, client);
        accountAfterCreateUpdateDto = accountMapper.toDtoAfterCreate(account);
        accountRepository.save(account);
        return accountAfterCreateUpdateDto;
    }

    private Account fillAccount(AccountCreateDto dto, Client client) {
        Account account;
        account = accountMapper.toEntity(dto);
        if (account.getBalance() == null) account.setBalance((double) 0);
        if (account.getStatus() == null) account.setStatus(AccountStatus.PENDING);
        if (account.getType() == null) account.setType(AccountType.CURRENT);
        account.setClient(client);
        return account;
    }

    @Override
    @Transactional
    public List<AccountAfterCreateUpdateDto> blockAccountByProductIdAndStatus(String productId, String status) {
        log.info("Change account status to BLOCKED for account with status {} and product id {} ", status, productId);
        List<Account> accountsByStatus = accountRepository.getAllByStatus(AccountStatus.valueOf(status));
        List<Account> accountsByStatusAndProductId = new ArrayList<>();
        Integer prodId = Integer.valueOf(productId);
        accountsByStatus.forEach(account -> {
            if (Objects.equals(account.getAgreement().getProduct().getId(), prodId)) {
                account.setStatus(AccountStatus.BLOCKED);
                account.setUpdatedAt(LocalDateTime.now());
                accountsByStatusAndProductId.add(account);
            }
        });
        if (accountsByStatusAndProductId.size() == 0) {
            log.warn(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID);
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID);
        }
        return new ArrayList<>(accountMapper.toListAfterCreateDto(accountRepository.saveAll(accountsByStatusAndProductId)));
    }
}

