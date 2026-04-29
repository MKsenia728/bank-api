package com.oksanamolchanova.bank.api.service.impl;

import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;
import com.oksanamolchanova.bank.api.entity.Client;
import com.oksanamolchanova.bank.api.entity.enums.CurrencyType;
import com.oksanamolchanova.bank.api.mapper.ClientMapper;
import com.oksanamolchanova.bank.api.repository.ClientRepository;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    final private ClientRepository clientRepository;

    final private ClientMapper clientMapper;


    @Override
    public List<ClientWithBalanceDto> getListClientsWithBalanceMoreThan(String balance, String currency) {
        log.info("Get list of client with balance more than {}", balance);
        double balanceD = Double.parseDouble(balance);
        CurrencyType currencyE = CurrencyType.valueOf(currency);
        List<Client> clientList = clientRepository.findByAccounts_BalanceGreaterThanEqualAndAccounts_CurrencyCode(balanceD, currencyE);
        if (clientList.size() == 0) {
            log.warn(ErrorMessage.CLIENTS_NOT_FOUND);
            throw new DataNotFoundException(ErrorMessage.CLIENTS_NOT_FOUND);
        }
        return new ArrayList<>(clientMapper.toListDtoWithBalance(clientList)) {
        };

    }
}
