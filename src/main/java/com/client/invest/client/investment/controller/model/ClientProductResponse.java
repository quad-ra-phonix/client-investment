package com.client.invest.client.investment.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ClientProductResponse extends ClientResponse implements Serializable {
    @JsonProperty("Client Products")
    private List<ProductResponse> products;

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}
