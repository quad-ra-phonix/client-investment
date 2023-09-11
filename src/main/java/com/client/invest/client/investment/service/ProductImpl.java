package com.client.invest.client.investment.service;

import com.client.invest.client.investment.exception.ProductNotFoundException;
import com.client.invest.client.investment.model.Client;
import com.client.invest.client.investment.model.Product;
import com.client.invest.client.investment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImpl implements IProduct {

    private final ProductRepository productRepository;

    private final IClient clientService;

    public ProductImpl(ProductRepository productRepository, IClient clientService) {
        this.productRepository = productRepository;
        this.clientService = clientService;
    }

    @Override
    public Product addProduct(Long clientId, Product product) {
        if (product == null) {
            return null;
        }
        Client client = clientService.fetchClientById(clientId);
        product.setClient(client);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        if (product == null) {
            return null;
        }

        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        productSaved.setName(product.getName());
        productSaved.setClient(product.getClient());
        productSaved.setProductType(product.getProductType());
        productSaved.setCurrentBalance(product.getCurrentBalance());
        return productRepository.save(productSaved);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        if (keyword == null) {
            return productRepository.findAll();
        }
        return productRepository.search(keyword);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public void updateBalance(Long productId, Double newCurrentBalance) {
        Product product = getProductById(productId);
        product.setCurrentBalance(newCurrentBalance);
        updateProduct(productId, product);
    }

    @Override
    public List<Product> getProductsByClientId(Long clientId) {
        if (clientId == null) {
            throw new NullPointerException();
        }

        Client client = clientService.fetchClientById(clientId);

        return productRepository.findProductByClientId(client);
    }
}
