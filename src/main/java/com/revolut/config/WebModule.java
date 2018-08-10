package com.revolut.config;

import com.google.inject.AbstractModule;
import com.revolut.web.impl.AccountRestResource;
import io.logz.guice.jersey.JerseyModule;
import io.logz.guice.jersey.configuration.JerseyConfiguration;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.json.spi.JsonProvider;

/**
 * Configuration of the application API layer.
 */
public class WebModule extends AbstractModule {

    public static final int DEFAULT_PORT = 8080;

    @Override
    protected void configure() {
        install(new JerseyModule(buildJerseyConfig()));
    }

    private JerseyConfiguration buildJerseyConfig(){
        return JerseyConfiguration.builder()
                .addPackage("com.revolut.web")
                .withResourceConfig(new ResourceConfig().register(JacksonFeature.class))
                .addPort(DEFAULT_PORT)
                .build();
    }
}
