package com.client.invest.client.investment.exception;

public class InvalidWithdrawalAmountPercentageException extends RuntimeException {
    public InvalidWithdrawalAmountPercentageException(){
        super("Withdrawal amount above request limit");
    }
}
