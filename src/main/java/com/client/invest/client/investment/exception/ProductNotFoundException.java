package com.client.invest.client.investment.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long productId) {

        super(String.format("Product with Id %d not found", productId));
    }
}
