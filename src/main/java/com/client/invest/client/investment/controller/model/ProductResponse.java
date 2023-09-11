package com.client.invest.client.investment.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProductResponse implements Serializable {
    @JsonProperty("Product Id")
    private Long id;
    @JsonProperty("Product Name")
    private String name;
    @JsonProperty("Product Type")
    private String productType;
    @JsonProperty("Current Balance")
    private Double currentBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
