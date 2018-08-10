package com.revolut.web.impl;

import com.revolut.service.AccountService;
import com.revolut.web.AccountApi;
import com.revolut.web.dto.AccountDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces({MediaType.APPLICATION_JSON})
public class AccountRestResource implements AccountApi {

    @Inject
    private AccountService accountService;


    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public AccountDTO createAccount(AccountDTO account) {
        return accountService.createAccount(account);
    }

    @Override
    @DELETE
    @Path("/{id}")
    public AccountDTO deleteAccount(@PathParam("id") Long accountId) {
        return accountService.deleteAccount(accountId);
    }

    @Override
    @GET
    @Path("/{id}")
    public AccountDTO getAccount(@PathParam("id") Long accountId) {
        return accountService.findAccount(accountId);
    }
}
