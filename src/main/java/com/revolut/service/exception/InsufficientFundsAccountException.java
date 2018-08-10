package com.revolut.service.exception;

import java.math.BigDecimal;

public class InsufficientFundsAccountException extends CommonAccountException{

    private final BigDecimal balance;

    public InsufficientFundsAccountException(Long accountId, BigDecimal balance) {
        super(accountId);
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String readableMessage() {
        return String.format("Account with id %s has not enough balance. Current balance %s", getAccountId(), getBalance());
    }
}
