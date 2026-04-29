package com.oksanamolchanova.bank.api.repository;

import com.oksanamolchanova.bank.api.entity.Account;
import com.oksanamolchanova.bank.api.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(UUID id);

    Optional<Account> findAccountByName(String name);

    List<Account> getAllBy();

    List<Account> getAllByStatus(AccountStatus status);

}
