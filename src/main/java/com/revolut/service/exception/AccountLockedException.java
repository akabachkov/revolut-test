package com.revolut.service.exception;

public class AccountLockedException extends CommonAccountException{

    public AccountLockedException(Long accountId) {
        super(accountId);
    }

    @Override
    public String readableMessage() {
        return String.format("Account with id %s is locked by concurrent transaction", getAccountId());
    }
}
