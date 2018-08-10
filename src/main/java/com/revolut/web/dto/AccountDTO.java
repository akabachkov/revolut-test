package com.revolut.web.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private String holder;
    private BigDecimal balance;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String holder, BigDecimal balance) {
        this.id = id;
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
}
