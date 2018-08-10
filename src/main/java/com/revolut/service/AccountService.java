package com.revolut.service;

import com.revolut.web.dto.AccountDTO;
import com.revolut.web.dto.TransactionResultDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface AccountService {

    /**
     * Create new account
     * @param account
     * @return created Account
     */
    AccountDTO createAccount(@NotNull AccountDTO account);

    /**
     * Delete account
     * @param accountId account identifier
     * @throws com.revolut.service.exception.AccountNotFoundException
     * @return deleted account or <b>null</b>
     */
    AccountDTO deleteAccount(@NotNull Long accountId);

    /**
     * Find account by id
     * @param accountId
     * @throws com.revolut.service.exception.AccountNotFoundException
     * @return
     */
    AccountDTO findAccount(@NotNull Long accountId);

    /**
     * Transfer money between accounts
     * @param creditAccountId accountId to withdraw money
     * @param debitAccountId  accountId to add money
     * @param amount amount
     * @throws com.revolut.service.exception.AccountNotFoundException
     * @throws com.revolut.service.exception.AccountLockedException
     * @throws com.revolut.service.exception.InsufficientFundsAccountException
     * @return result of transaction
     */
    TransactionResultDTO makeTransaction(Long creditAccountId, Long debitAccountId, BigDecimal amount);
}
