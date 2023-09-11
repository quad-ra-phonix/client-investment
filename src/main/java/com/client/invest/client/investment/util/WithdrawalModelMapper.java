package com.client.invest.client.investment.util;

import com.client.invest.client.investment.controller.model.WithdrawalResponse;
import com.client.invest.client.investment.model.Withdrawal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WithdrawalModelMapper {

    public static WithdrawalResponse mapToResponse(Withdrawal withdrawal) {
        WithdrawalResponse response = new WithdrawalResponse();
        response.setId(withdrawal.getId());
        response.setWithdrawalAmount(withdrawal.getAmount());
        response.setWithdrawalStatus(withdrawal.getStatus().name());

        response.setProductId(withdrawal.getProduct().getId());
        response.setProductName(withdrawal.getProduct().getName());
        response.setProductType(withdrawal.getProduct().getProductType().name());
        response.setProductBalance(withdrawal.getProduct().getCurrentBalance());

        response.setClientId(withdrawal.getProduct().getClient().getId());
        response.setClientName(withdrawal.getProduct().getClient().getFirstName() + " " + withdrawal.getProduct().getClient().getLastName());

        return response;
    }

    public static List<WithdrawalResponse> mapResponseList(List<Withdrawal> withdrawals) {
        List<WithdrawalResponse> responses = new ArrayList<>();
        withdrawals.forEach(c -> {
            responses.add(mapToResponse(c));
        });
        return responses;
    }
}
