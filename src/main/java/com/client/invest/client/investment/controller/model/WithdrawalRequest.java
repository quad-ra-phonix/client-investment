package com.client.invest.client.investment.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class WithdrawalRequest implements Serializable {

    @JsonProperty("Product Id")
    @NotNull(message = "Produt Id is required")
    private Long productId;
    @JsonProperty("Withdrawal Amount")
    private Double amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
