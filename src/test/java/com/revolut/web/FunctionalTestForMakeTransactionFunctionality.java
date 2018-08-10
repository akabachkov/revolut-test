package com.revolut.web;

import com.google.inject.Guice;
import com.revolut.App;
import com.revolut.TestConfig;
import com.revolut.config.DaoModule;
import com.revolut.config.ServiceModule;
import com.revolut.config.WebModule;
import com.revolut.web.dto.AccountDTO;
import com.revolut.web.dto.TransactionDTO;
import com.revolut.web.dto.TransactionResultDTO;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

public class FunctionalTestForMakeTransactionFunctionality {

    private App application;

    private WebTarget target;

    @Before
    public void setUp() {
        application = new App(
        Guice.createInjector(
                new WebModule(),
                new DaoModule(TestConfig.DB_CONFIG),
                new ServiceModule()));
        application.start();
        target = ClientBuilder.newClient().register(JacksonFeature.class).target("http://localhost:8080/");

    }

    @After
    public void tearDown(){
        application.stop();
    }

    @Test
    public void testThatFeatureOfTransferMoneyReallyWorks(){
        //Testing create 2 balance
        //when
        AccountDTO one = target.path("account").request(MediaType.APPLICATION_JSON_TYPE).buildPut(Entity.json(new AccountDTO(null,"client1",new BigDecimal(100)))).invoke(AccountDTO.class);
        AccountDTO two = target.path("account").request(MediaType.APPLICATION_JSON_TYPE).buildPut(Entity.json(new AccountDTO(null,"client2",new BigDecimal(100)))).invoke(AccountDTO.class);
        //then
        Assert.assertNotNull(one);
        Assert.assertNotNull(two);
        Assert.assertNotNull(one.getId());
        Assert.assertNotNull(two.getId());
        Assert.assertEquals("client1", one.getHolder());
        Assert.assertEquals("client2", two.getHolder());
        Assert.assertEquals(100, one.getBalance().intValue());
        Assert.assertEquals(100, two.getBalance().intValue());

        //Testing transaction between accounts
        TransactionDTO transactionRequest = new TransactionDTO(one.getId(), two.getId(),new BigDecimal(50));
        TransactionResultDTO result = target.path("transaction").request(MediaType.APPLICATION_JSON_TYPE).buildPost(Entity.json(transactionRequest)).invoke(TransactionResultDTO.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(50,result.getCreditAccount().getBalance().intValue());
        Assert.assertEquals(150,result.getDebitAccount().getBalance().intValue());

        //Testing that result persisted
        one = target.path("account").path(one.getId().toString()).request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(AccountDTO.class);
        two = target.path("account").path(two.getId().toString()).request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(AccountDTO.class);
        Assert.assertEquals(50, one.getBalance().intValue());
        Assert.assertEquals(150, two.getBalance().intValue());
    }

}
