package com.revolut.web;

import com.revolut.web.dto.AccountDTO;

/**
 * API for managing accounts
 */
public interface AccountApi {

    AccountDTO createAccount(AccountDTO account);

    AccountDTO deleteAccount(Long accountId);

    AccountDTO getAccount(Long accountId);

}
