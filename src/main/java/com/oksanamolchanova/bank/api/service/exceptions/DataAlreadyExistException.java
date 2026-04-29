package com.oksanamolchanova.bank.api.service.exceptions;

public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
