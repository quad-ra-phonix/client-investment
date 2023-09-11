package com.client.invest.client.investment.repository;

import com.client.invest.client.investment.model.Client;
import com.client.invest.client.investment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.client = :client")
    List<Product> findProductByClientId(@Param("client") Client clientId);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.productType) LIKE %?1%")
    List<Product> search(String keyword);
}
