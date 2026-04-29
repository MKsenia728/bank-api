package com.oksanamolchanova.bank.api.service.exceptions;

public class ErrorMessage {
    public static final String INVALID_PATH_VARIABLE = "Variable for path is invalid";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String ACCOUNT_NOT_FOUND_BY_ID = "Account was not found by this Id";
    public static final String ACCOUNT_NOT_FOUND_BY_NAME = "Account was not found by this name";
    public static final String ACCOUNTS_NOT_FOUND = "Any accounts not found";
    public static final String ACCOUNTS_NOT_FOUND_BY_STATUS = "No accounts found by status";
    public static final String ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID = "No accounts found by status and product id";
    public static final String ACCOUNT_ALREADY_EXISTS = "Account already exist";
    public static final String CLIENT_NOT_FOUND_BY_TAX_CODE = "Client not found by tax code";
    public static final String CLIENTS_NOT_FOUND = "Any clients not found";
    public static final String MANAGER_NOT_FOUND = "Manager not found by this Id";
    public static final String MANAGERS_NOT_FOUND = "Managers not found by this Id";
    public static final String MANAGER_ALREADY_EXISTS= "Manager with these name and lastname and status already exist";
}
