package com.revolut;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.config.DaoModule;
import com.revolut.config.ServiceModule;
import com.revolut.config.WebModule;
import io.logz.guice.jersey.JerseyServer;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private final Injector injector;

    public App(Injector injector) {
        this.injector = injector;
    }

    public static void main(String[] args) {
        Map<String, String> config = readConfig();
        App demoApp = new App(Guice.createInjector(new WebModule(), new DaoModule(config), new ServiceModule()));
        demoApp.registerShutdownHook();
        demoApp.start();
    }

    private static Map<String, String> readConfig() {
        Map<String, String> config = new HashMap<>();
        config.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
        config.put(PersistenceUnitProperties.JDBC_DRIVER, "org.h2.Driver");
        config.put(PersistenceUnitProperties.JDBC_URL, "jdbc:h2:~/test");
        config.put(PersistenceUnitProperties.JDBC_USER, "sa");
        config.put(PersistenceUnitProperties.JDBC_PASSWORD, "");
        return config;
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread("hook"){
            @Override
            public void run() {
                App.this.stop();
            }
        });
    }

    public void start(){
        JerseyServer server = injector.getInstance(JerseyServer.class);
        try {
            server.start();
        } catch (Exception e) {
            LOG.error("Failed to start application", e);
            throw new RuntimeException("Failed to start application",e);
        }
    }

    public void stop(){
        JerseyServer server = injector.getInstance(JerseyServer.class);
        try {
            server.stop();
        } catch (Exception e) {
            LOG.error("Failed to stop application", e);
            throw new RuntimeException("Failed to stop application",e);
        }
    }
}
