package com.client.invest.client.investment.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WithdrawalResponse implements Serializable {

    @JsonProperty("Withdrawal Id")
    private Long id;

    @JsonProperty("Withdrawal Amount")
    private Double withdrawalAmount;
    @JsonProperty("Withdrawal Status")
    private String withdrawalStatus;

    @JsonProperty("Product Id")
    private Long productId;
    @JsonProperty("Product Name")
    private String productName;
    @JsonProperty("Product Type")
    private String productType;
    @JsonProperty("Product Current Balance")
    private Double productBalance;
    @JsonProperty("Client Id")
    private Long clientId;
    @JsonProperty("Client Name")
    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(String withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getProductBalance() {
        return productBalance;
    }

    public void setProductBalance(Double productBalance) {
        this.productBalance = productBalance;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
