package com.revolut.dao;

import com.revolut.dao.model.Account;

import javax.validation.constraints.NotNull;

/**
 * Access layer to Account objects
 */
public interface AccountDao {

    /**
     * save new account to storage.
     * @param account account object
     * @return saved account
     * @throws IllegalArgumentException in case of save operation fail.
     */
    Account save(@NotNull Account account);

    /**
     * Find account by identifier
     * @param id identifie
     * @return found account or <b>null</b>
     */
    Account findById(@NotNull Long id);

    /**
     * update existing account to storage
     * @param account
     * @return
     * @throws IllegalArgumentException in case of save operation fail.
     */
    Account update(@NotNull Account account);

    /**
     * Remove account from storage
     * @param id account identifier
     * @return deleted account or <b>null</b>
     * @throws IllegalArgumentException in case of delete operation fail.
     */
    Account deleteById(@NotNull Long id);

    /**
     * Lock account for preventing update in concurrent context.
     * <p>
     *     <b>Important:</b> this operation should be called in transaction context.
     * </p>
     * @param accountId accountId of account to be locked
     * @throws IllegalStateException if account already locked
     * @return locked account or <b>null</b> if it is not exists
     */
    Account lock(@NotNull Long accountId);
}
