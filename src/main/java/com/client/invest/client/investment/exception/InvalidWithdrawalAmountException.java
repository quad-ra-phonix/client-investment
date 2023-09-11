package com.client.invest.client.investment.exception;

public class InvalidWithdrawalAmountException extends RuntimeException{
    public InvalidWithdrawalAmountException(){
        super("Requested withdrawal amount is more than current balance");
    }
}
