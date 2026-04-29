package com.oksanamolchanova.bank.api.service.interf;

import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;

import java.util.List;

public interface ClientService {

    List<ClientWithBalanceDto> getListClientsWithBalanceMoreThan(String balance, String currency);
}
