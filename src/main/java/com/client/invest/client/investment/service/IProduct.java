package com.client.invest.client.investment.service;

import com.client.invest.client.investment.model.Product;

import java.util.List;

public interface IProduct {
    Product addProduct(Long clientId, Product product);

    Product updateProduct(Long productId, Product product);

    List<Product> searchProducts(String keyword);

    Product getProductById(Long productId);

    void updateBalance(Long productId, Double newCurrentBalance);

    List<Product> getProductsByClientId(Long clientId);

}
