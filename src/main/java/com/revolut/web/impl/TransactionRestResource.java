package com.revolut.web.impl;

import com.revolut.service.AccountService;
import com.revolut.web.AccountApi;
import com.revolut.web.TransactionApi;
import com.revolut.web.dto.AccountDTO;
import com.revolut.web.dto.TransactionDTO;
import com.revolut.web.dto.TransactionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionRestResource implements TransactionApi {

    @Inject
    private AccountService accountService;

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionResultDTO makeTransaction(TransactionDTO transaction) {
        return accountService.makeTransaction(transaction.getCreditAccountId(), transaction.getDebitAccountId(), transaction.getAmount());
    }
}
