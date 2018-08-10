package com.revolut.service.exception;

/**
 * Base exception for account errors.
 */
public abstract class CommonAccountException extends RuntimeException {

    private final Long accountId;

    public CommonAccountException(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public abstract String readableMessage();
}
