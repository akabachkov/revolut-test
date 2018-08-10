package com.revolut.config;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.revolut.dao.AccountDao;
import com.revolut.dao.impl.AccountDaoJpaImpl;

import java.util.Map;

public class DaoModule extends AbstractModule {

    private Map<String, String> connectionProperties;

    public DaoModule(Map<String, String> connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    @Override
    protected void configure() {
        install(createJpaModule());
        bind(AccountDao.class).to(AccountDaoJpaImpl.class).in(Singleton.class);
        bind(Initializer.class).asEagerSingleton();
    }

    private JpaPersistModule createJpaModule() {
        final JpaPersistModule jpaModule = new JpaPersistModule("DefaultUnit");
        jpaModule.properties(connectionProperties);
        return jpaModule;
    }

    public static class Initializer {

        @Inject
        public Initializer(PersistService service) {
            service.start();
        }
    }
}
