package com.client.invest.client.investment.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(Long age){
        super(String.format("Client age: %d, not valid", age));
    }
}
