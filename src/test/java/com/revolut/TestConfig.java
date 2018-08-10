package com.revolut;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import java.util.HashMap;
import java.util.Map;

public class TestConfig {

    public static final Map<String,String> DB_CONFIG = new HashMap(){{
        put(PersistenceUnitProperties.JDBC_DRIVER, "org.h2.Driver");
        put(PersistenceUnitProperties.JDBC_URL, "jdbc:h2:~/test");
        put(PersistenceUnitProperties.JDBC_USER, "sa");
        put(PersistenceUnitProperties.JDBC_PASSWORD, "");
        put(PersistenceUnitProperties.PERSISTENCE_CONTEXT_FLUSH_MODE, "COMMIT");
        put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
    }};
}
