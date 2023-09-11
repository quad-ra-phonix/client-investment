package com.client.invest.client.investment.model;

import com.client.invest.client.investment.enums.WithdrawalStates;

import javax.persistence.*;

@Entity
@Table(name = "withdrawal")
public class Withdrawal extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private WithdrawalStates status;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_withdrawal_product"))
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public WithdrawalStates getStatus() {
        return status;
    }

    public void setStatus(WithdrawalStates status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
