package com.oksanamolchanova.bank.api.service.interf;

import com.oksanamolchanova.bank.api.dto.AccountAfterCreateUpdateDto;
import com.oksanamolchanova.bank.api.dto.AccountCreateDto;
import com.oksanamolchanova.bank.api.dto.AccountDto;
import com.oksanamolchanova.bank.api.dto.AccountNameDto;

import java.util.List;

public interface AccountService {

    AccountDto getAccountById(String id);
    AccountDto getAccountByName(String name);

    List<AccountNameDto> getAllAccounts();
    List<AccountDto> getAllAccountsByStatus(String Status);

    AccountAfterCreateUpdateDto createNewAccount(AccountCreateDto accountCreateDto, String clientTaxCode);

    List<AccountAfterCreateUpdateDto> blockAccountByProductIdAndStatus(String productId, String status);
}
