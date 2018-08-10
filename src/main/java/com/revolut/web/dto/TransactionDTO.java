package com.revolut.web.dto;

import java.math.BigDecimal;

public class TransactionDTO {

    /**
     * Identifier of account from where money will be withdrown.
     */
    private Long creditAccountId;
    /**
     * Identifier of account where money will be added.
     */
    private Long debitAccountId;
    /**
     * Amount of money. no currencies for simplicity.
     */
    private BigDecimal amount;

    public TransactionDTO() {
    }

    public TransactionDTO(Long creditAccountId, Long debitAccountId, BigDecimal amount) {
        this.creditAccountId = creditAccountId;
        this.debitAccountId = debitAccountId;
        this.amount = amount;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
