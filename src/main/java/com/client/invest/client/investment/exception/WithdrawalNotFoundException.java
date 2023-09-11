package com.client.invest.client.investment.exception;

public class WithdrawalNotFoundException extends RuntimeException{
    public WithdrawalNotFoundException(Long withdrawalId){
        super(String.format("Withdrawal with Id %d not found", withdrawalId));
    }
}
