package com.client.invest.client.investment.service;

import com.client.invest.client.investment.enums.WithdrawalEvents;
import com.client.invest.client.investment.enums.WithdrawalStates;
import com.client.invest.client.investment.model.Withdrawal;
import org.springframework.statemachine.StateMachine;

import java.util.List;

public interface IWithdrawal {
    Withdrawal createWithdrawal(Long productId, Double withdrawalAmount);

    List<Withdrawal> fetchAllWithdrawals();

    Withdrawal getWithdrawalById(Long withdrawalId);

    StateMachine<WithdrawalStates, WithdrawalEvents> execute(Long withdrawalId);

    StateMachine<WithdrawalStates, WithdrawalEvents> done(Long withdrawalId);
}
