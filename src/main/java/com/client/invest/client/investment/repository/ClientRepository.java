package com.client.invest.client.investment.repository;

import com.client.invest.client.investment.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %?1%")
    List<Client> search(String keyword);
}
