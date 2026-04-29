package com.oksanamolchanova.bank.api.util;

import com.oksanamolchanova.bank.api.entity.*;
import com.oksanamolchanova.bank.api.entity.enums.*;
import com.oksanamolchanova.bank.api.entity.*;
import com.oksanamolchanova.bank.api.entity.enums.*;

import java.time.LocalDateTime;
import java.util.HashSet;

public class EntityCreator {
    public static final String UUID = "00001d7f-d14f-4655-9399-25bf27b16588";
    public static final String NAME = "TT 89 311045 00234355921201";


    public static Account getAccountEntity() {
        Agreement agreement = getAgreementEntity();
        Client client = getClientEntity();
        return new Account(
                java.util.UUID.fromString(UUID),
                NAME,
                AccountType.CURRENT,
                AccountStatus.ACTIVE,
                10000.0,
                CurrencyType.EUR,
                LocalDateTime.of(2021,12,2,9,0,0),
                LocalDateTime.of(2021,12,2,9,0,0),
                client,
                null,
                null,
                agreement
        );
    }

    public static Account getCreateAccountEntity() {
        Agreement agreement = getAgreementEntity();
        return new Account(
                java.util.UUID.fromString(UUID),
                NAME,
                AccountType.CURRENT,
                AccountStatus.PENDING,
                10000.0,
                CurrencyType.EUR,
                LocalDateTime.of(2021,12,2,9,0,0),
                LocalDateTime.of(2021,12,2,9,0,0),
                getClientEntity(),
                null,
                null,
                agreement
        );
    }


    public static Client getClientEntity() {
        return new Client(
                java.util.UUID.fromString("0011d7f-d14f-4655-9399-25bf27b16588"),
                ClientStatus.ACTIVE,
                "123123123123",
                "Ivan",
                "Tester",
                "ivan@gmail.com",
                "Berlin",
                "+111111111111111",
                LocalDateTime.of(2021,12,2,9,0,0),
                LocalDateTime.of(2021,12,2,9,0,0),
                new HashSet<>(),
                new Manager()
        );
    }
    public static Agreement getAgreementEntity() {
        Agreement agreement = new Agreement();
        agreement.setId(1);
        agreement.setProduct(getProductEntity());
        return agreement;
    }

    public static Product getProductEntity() {
        Product product = new Product();
        product.setId(1);
        return product;
    }
    public static Manager getManagerEntity() {
        return new Manager(
                1L,
                "Petr",
                "Popov",
                ManagerStatus.ACTIVE,
                LocalDateTime.of(2021,12,2,9,0,0),
                LocalDateTime.of(2021,12,2,9,0,0),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public static Manager getCreateManagerEntity() {
        return new Manager(
                1L,
                "Petr",
                "Popov",
                ManagerStatus.PENDING,
                LocalDateTime.of(2021,12,2,9,0,0),
                LocalDateTime.of(2021,12,2,9,0,0),
                new HashSet<>(),
                new HashSet<>()
        );
    }
}
