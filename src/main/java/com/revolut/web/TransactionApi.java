package com.revolut.web;

import com.revolut.web.dto.TransactionDTO;
import com.revolut.web.dto.TransactionResultDTO;

/**
 * API for transfer money
 */
public interface TransactionApi {

    /**
     * Perform transaction
     * @param transaction
     * @return
     */
    TransactionResultDTO makeTransaction(TransactionDTO transaction);

}
