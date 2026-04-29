package com.oksanamolchanova.bank.api.mapper;

import com.oksanamolchanova.bank.api.dto.ClientDto;
import com.oksanamolchanova.bank.api.dto.ClientWithBalanceDto;
import com.oksanamolchanova.bank.api.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, uses = {AccountMapper.class})
public interface ClientMapper {
    ClientDto toDto(Client client);

    @Mapping(source = "client.accounts", target="balanceAndCurrency")
    ClientWithBalanceDto toDtoWithBalance(Client client);

    Client toEntity(ClientDto clientDto);

    List<ClientWithBalanceDto> toListDtoWithBalance(List<Client> clients);
}
