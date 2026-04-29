package com.oksanamolchanova.bank.api.mapper;

import com.oksanamolchanova.bank.api.dto.*;
import com.oksanamolchanova.bank.api.dto.*;
import com.oksanamolchanova.bank.api.entity.Account;
import com.oksanamolchanova.bank.api.entity.enums.AccountStatus;
import com.oksanamolchanova.bank.api.entity.enums.AccountType;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Account mapper test class")
class AccountMapperTest {
    private final AccountMapper accountMapper = new AccountMapperImpl();

    private final Account account = EntityCreator.getAccountEntity();

    private List<Account> accountList;

    @DisplayName("Positive test. Account mapper to DTO test")
    @Test
    void toDtoTest() {
        AccountDto expectedAccountDto = DtoCreator.getAccountDto();
        AccountDto actualAccountDto = accountMapper.toDto(account);
//        Assertions.assertEquals(expectedAccountDto, accountMapper.toDto(account));
        Assertions.assertEquals(expectedAccountDto.id(), actualAccountDto.id());
        Assertions.assertEquals(expectedAccountDto.name(), actualAccountDto.name());
        Assertions.assertEquals(expectedAccountDto.balance(), actualAccountDto.balance());
        Assertions.assertEquals(expectedAccountDto.status(), actualAccountDto.status());
        Assertions.assertEquals(expectedAccountDto.type(), actualAccountDto.type());
        Assertions.assertEquals(expectedAccountDto.currencyCode(), actualAccountDto.currencyCode());
        Assertions.assertEquals(expectedAccountDto.clientFirstName(), actualAccountDto.clientFirstName());
        Assertions.assertEquals(expectedAccountDto.clientLastName(), actualAccountDto.clientLastName());
        Assertions.assertEquals(expectedAccountDto.createdAt(), actualAccountDto.createdAt());
        Assertions.assertEquals(expectedAccountDto.updatedAt(), actualAccountDto.updatedAt());
    }

    @DisplayName("Negative test. Null mapper to DTO test")
    @Test
    void toDtoNullTest() {
        Assertions.assertNull(accountMapper.toDto(null));
    }

    @DisplayName("Positive test. Account mapper to DTO balance and currency test")
    @Test
    void toDtoBalanceAndCurrencyTest() {
        AccountBalanceDto accountBalanceDto = DtoCreator.getBalanceAndCurrencyDto();
        Assertions.assertEquals(accountBalanceDto, accountMapper.toDtoBalanceAndCurrency(account));
    }

    @DisplayName("Negative test. Null mapper to DTO balance and currency test")
    @Test
    void toDtoBalanceAndCurrencyNullTest() {
        Assertions.assertNull(accountMapper.toDtoBalanceAndCurrency(null));
    }

    @DisplayName("Positive test. Account mapper to DTO after creating")
    @Test
    void toDtoAfterCreateTest() {
        AccountAfterCreateUpdateDto expectedAccountDto = DtoCreator.getAccountAfterCreateDto("ACTIVE");
        Assertions.assertEquals(expectedAccountDto, accountMapper.toDtoAfterCreate(account));
    }

    @DisplayName("Negative test. Account null mapper to DTO after creating")
    @Test
    void toDtoAfterCreateNulTest() {
        Assertions.assertNull(accountMapper.toDtoAfterCreate(null));
    }

    @DisplayName("Positive test. Create from DTO to account")
    @Test
    void toEntityTest() {
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDto();
        Assertions.assertEquals(account, accountMapper.toEntity(accountCreateDto));
    }

    @DisplayName("Positive test. Create from DTO to account, check Type")
    @Test
    void toEntityCheckTypeTest() {
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDtoWithType();
        Assertions.assertEquals(AccountType.CURRENT, accountMapper.toEntity(accountCreateDto).getType());
    }

    @DisplayName("Positive test. Create from DTO to account, check Status")
    @Test
    void toEntityCheckStatusTest() {
        AccountCreateDto accountCreateDto = DtoCreator.getAccountCreateDtoWithStatus();
        Assertions.assertEquals(AccountStatus.ACTIVE, accountMapper.toEntity(accountCreateDto).getStatus());
    }

    @DisplayName("Negative test. Create from null to account")
    @Test
    void toEntityNullTest() {
        Assertions.assertNull(accountMapper.toEntity(null));
    }

    @DisplayName("Positive test. Accounts list mapper to list DTO")
    @Test
    void toListDtoTest() {
        initListAccounts();
        AccountDto expectedAccountDto = DtoCreator.getAccountDto();
        List<AccountDto> expectedAccountDtoList = new ArrayList<>();
        expectedAccountDtoList.add(expectedAccountDto);
        Assertions.assertEquals(expectedAccountDtoList, accountMapper.toListDto(accountList));
    }

    @DisplayName("Negative test. Accounts list null mapper to list DTO")
    @Test
    void toListDtoNullTest() {
        Assertions.assertNull(accountMapper.toListDto(null));
    }

    @DisplayName("Positive test. Accounts list mapper to list name DTO")
    @Test
    void toListBalanceAndCurrencyDtoTest() {
        initListAccounts();
        AccountNameDto expectedAccountDto = DtoCreator.getAccountNameDto();
        List<AccountNameDto> expectedAccountDtoList = new ArrayList<>();
        expectedAccountDtoList.add(expectedAccountDto);
        Assertions.assertEquals(expectedAccountDtoList, accountMapper.toListDtoName(accountList));
    }

    @DisplayName("Negative test. Accounts list null mapper to list name DTO")
    @Test
    void toListBalanceAndCurrencyDtoNullTest() {
        Assertions.assertNull(accountMapper.toListDtoName(null));
    }

    @DisplayName("Positive test. Accounts list mapper to list after create DTO")
    @Test
    void toListAfterCreateDtoTest() {
        initListAccounts();
        AccountAfterCreateUpdateDto accountAfterCreateUpdateDto = DtoCreator.getAccountAfterCreateDto("ACTIVE");
        List<AccountAfterCreateUpdateDto> expectedAccountAfterCreateUpdateDtoList = new ArrayList<>();
        expectedAccountAfterCreateUpdateDtoList.add(accountAfterCreateUpdateDto);

        Assertions.assertEquals(expectedAccountAfterCreateUpdateDtoList, accountMapper.toListAfterCreateDto(accountList));
    }

    @DisplayName("Negative test. Account list null mapper to list after create DTO")
    @Test
    void toListAfterCreateDtoNullTest() {
        Assertions.assertNull(accountMapper.toListAfterCreateDto(null));
    }

    private void initListAccounts() {
        accountList = new ArrayList<>();
        accountList.add(account);
    }
}