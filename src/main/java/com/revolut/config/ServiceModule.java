package com.revolut.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.revolut.service.AccountService;
import com.revolut.service.impl.AccountServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class).in(Singleton.class);
    }
}
