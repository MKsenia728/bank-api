package com.oksanamolchanova.bank.api.entity.enums;

public enum CurrencyType {
    EUR("EUR"),

    USD("USD"),

    UAH("UAH"),

    RUB("RUB");

    private final String value;

    CurrencyType(String  value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
