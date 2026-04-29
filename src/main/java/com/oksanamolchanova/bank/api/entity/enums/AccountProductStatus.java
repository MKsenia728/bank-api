package com.oksanamolchanova.bank.api.entity.enums;

public enum AccountProductStatus {
    ACTIVE("ACTIVE"),
    
    PENDING("PENDING"),

    BLOCKED("BLOCKED"),

    REMOVED("REMOVED");

    private final String value;

    AccountProductStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
