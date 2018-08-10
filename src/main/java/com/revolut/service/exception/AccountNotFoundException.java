package com.revolut.service.exception;

public class AccountNotFoundException extends CommonAccountException{

    public AccountNotFoundException(Long accountId) {
        super(accountId);
    }

    @Override
    public String readableMessage() {
        return String.format("Account with id %s not found", getAccountId());
    }
}
