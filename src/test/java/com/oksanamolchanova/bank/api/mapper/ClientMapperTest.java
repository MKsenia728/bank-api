package com.oksanamolchanova.bank.api.mapper;

import com.oksanamolchanova.bank.api.dto.ClientDto;
import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;
import com.oksanamolchanova.bank.api.entity.Account;
import com.oksanamolchanova.bank.api.entity.Client;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DisplayName("Client mapper test class")
class ClientMapperTest {
    private final ClientMapper clientMapper = new ClientMapperImpl(new AccountMapperImpl());

    private final Client client = EntityCreator.getClientEntity();

    @DisplayName("Positive test. Client mapper to DTO")
    @Test
    void toDtoTest() {
        ClientDto expectedClientDto = DtoCreator.getClientDto();
        Assertions.assertEquals(expectedClientDto, clientMapper.toDto(client));
    }

    @DisplayName("Negative test. Client null mapper to DTO")
    @Test
    void toDtoNullTest() {
        Assertions.assertNull(clientMapper.toDto(null));
    }

    @DisplayName("Positive test. Client mapper to ClientWithBalanceAndCurrencyDTO")
    @Test
    void toDtoWithBalanceTest() {
        initForClientWithBalanceDtoTests();

        ClientWithBalanceDto expectedClientDto = DtoCreator.getClientWithBalanceDto();
        Assertions.assertEquals(expectedClientDto, clientMapper.toDtoWithBalance(client));
    }

    @DisplayName("Negative test. Client null mapper to ClientWithBalanceAndCurrencyDTO")
    @Test
    void toDtoWithBalanceNullTest() {
        Assertions.assertNull(clientMapper.toDtoWithBalance(null));
    }

    @DisplayName("Positive test. Create client from DTO")
    @Test
    void toEntityTest() {
        ClientDto clientDto = DtoCreator.getClientDto();
        Assertions.assertEquals(client, clientMapper.toEntity(clientDto));
    }

    @DisplayName("Negative test. Create client from DTO null")
    @Test
    void toEntityNullTest() {
        Assertions.assertNull(clientMapper.toEntity(null));
    }

    @DisplayName("Positive test. Clients list with balance mapper to list with balance and currency DTO")
    @Test
    void toListDtoWithBalanceTest() {
        initForClientWithBalanceDtoTests();

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);
        ClientWithBalanceDto expectedClientDto = DtoCreator.getClientWithBalanceDto();
        List<ClientWithBalanceDto> expectedClientWithBalanceDtoList = new ArrayList<>();
        expectedClientWithBalanceDtoList.add(expectedClientDto);

        Assertions.assertEquals(expectedClientWithBalanceDtoList, clientMapper.toListDtoWithBalance(clientList));
    }

    @DisplayName("Negative test. Clients list null with balance mapper to list with balance and currency DTO")
    @Test
    void toListDtoWithBalanceNullTest() {
        Assertions.assertNull(clientMapper.toListDtoWithBalance(null));
    }

    private void initForClientWithBalanceDtoTests() {
        Account account = EntityCreator.getAccountEntity();
        Set<Account> accountList = new HashSet<>();
        accountList.add(account);
        client.setAccounts(accountList);
    }
}