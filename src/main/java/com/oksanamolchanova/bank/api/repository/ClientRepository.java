package com.oksanamolchanova.bank.api.repository;

import com.oksanamolchanova.bank.api.entity.Client;
import com.oksanamolchanova.bank.api.entity.enums.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findClientByTaxCode(String taxCode);

    List<Client> findByAccounts_BalanceGreaterThanEqualAndAccounts_CurrencyCode(Double balance, CurrencyType currency);
}

