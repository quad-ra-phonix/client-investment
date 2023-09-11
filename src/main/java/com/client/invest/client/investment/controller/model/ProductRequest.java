package com.client.invest.client.investment.controller.model;

import com.client.invest.client.investment.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProductRequest implements Serializable {
    @JsonProperty("Product Name")
    private String name;
    @JsonProperty("Product Type")
    private ProductType productType;
    @JsonProperty("Current Balance")
    private Double currentBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
