package com.client.invest.client.investment.util;

import com.client.invest.client.investment.controller.model.ProductRequest;
import com.client.invest.client.investment.controller.model.ProductResponse;
import com.client.invest.client.investment.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductModelMapper {

    public static Product mapToEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setProductType(request.getProductType());
        product.setCurrentBalance(request.getCurrentBalance());
        return product;
    }

    public static ProductResponse mapToResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setProductType(product.getProductType().name());
        response.setCurrentBalance(product.getCurrentBalance());
        return response;
    }

    public static List<ProductResponse> mapResponseList(List<Product> customers) {
        List<ProductResponse> responses = new ArrayList<>();
        customers.forEach(c -> {
            responses.add(mapToResponse(c));
        });
        return responses;
    }
}
