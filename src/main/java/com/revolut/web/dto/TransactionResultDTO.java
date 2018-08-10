package com.revolut.web.dto;

public class TransactionResultDTO {

    private AccountDTO debitAccount;

    private AccountDTO creditAccount;

    public TransactionResultDTO() {
    }

    public TransactionResultDTO(AccountDTO debitAccount, AccountDTO creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public AccountDTO getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(AccountDTO debitAccount) {
        this.debitAccount = debitAccount;
    }

    public AccountDTO getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(AccountDTO creditAccount) {
        this.creditAccount = creditAccount;
    }
}
