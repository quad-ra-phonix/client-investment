package com.client.invest.client.investment.repository;

import com.client.invest.client.investment.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
