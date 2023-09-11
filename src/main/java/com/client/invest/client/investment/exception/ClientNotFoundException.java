package com.client.invest.client.investment.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long clientId) {

        super(String.format("Client with Id %d not found", clientId));
    }
}
