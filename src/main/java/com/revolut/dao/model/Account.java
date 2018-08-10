package com.revolut.dao.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    /**
     * unique indentifier of account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * just name of holder for simplicity.
     */
    @Column(name = "holder")
    private String holder;

    /**
     * balance of the account;
     */
    @Column(name = "balance", columnDefinition = "NUMBER")
    private BigDecimal balance;


    public Account() {
    }

    public Account(String holder, BigDecimal balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(holder, account.holder) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holder, balance);
    }
}
