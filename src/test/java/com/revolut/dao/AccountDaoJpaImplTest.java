package com.revolut.dao;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.revolut.TestConfig;
import com.revolut.config.DaoModule;
import com.revolut.dao.model.Account;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class AccountDaoJpaImplTest {

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    @Inject
    private AccountDao testSubject;

    @Inject
    private Provider<EntityManager> em;

    @Before
    public void setUp() {
        Guice.createInjector(new DaoModule(TestConfig.DB_CONFIG)).injectMembers(this);
    }

    @Test
    public void testThatSaveCreatesAccount() throws ExecutionException, InterruptedException {
        //given
        Account toBePersisted = dummy("testMe");
        //when
        Account persisted = testSubject.save(toBePersisted);
        //then
        Assert.assertNotNull(toBePersisted);
        Assert.assertNotNull(toBePersisted.getId());
        Account foundInDb = inSeparateThread(() -> em.get().find(Account.class, persisted.getId())).get();
        Assert.assertNotNull(foundInDb);
        Assert.assertEquals(toBePersisted, foundInDb);
    }



    @Test
    public void testFindByIdFindExistingAccount() {
        //given
        Account saved = saveAccount(dummy("testMe"));
        //when
        Account found = testSubject.findById(saved.getId());
        //then
        Assert.assertNotNull(found);
        Assert.assertEquals(saved, found);
    }

    @Test
    public void testThatUpdateModifiesExistingAccount() throws ExecutionException, InterruptedException {
        //given
        Account saved = saveAccount(dummy("testMe"));
        //when
        saved.setHolder("updated");
        testSubject.update(saved);
        //then
        Account found = inSeparateThread(() -> em.get().find(Account.class, saved.getId())).get();
        Assert.assertEquals("updated", found.getHolder());
    }

    @Test
    public void testThatDeleteByIdRemovesAccount() throws ExecutionException, InterruptedException {
        //given
        Account given = saveAccount(dummy("testMe"));
        //when
        testSubject.deleteById(given.getId());
        //then
        Account found = inSeparateThread(() -> em.get().find(Account.class, given.getId())).get();
        Assert.assertNull(found);
    }

    @Test
    public void testThatLockPreventUpdatesAccount() throws InterruptedException, ExecutionException, TimeoutException {
        //given
        Long id = saveAccount(dummy("testMe")).getId();
        //when
        inSeparateThread(()-> {
           //begin outer transaction
           em.get().getTransaction().begin();
           Account lockedAccount = testSubject.lock(id);
           //emulation some work in separate transacton;
           Thread.sleep(5000);
           em.get().getTransaction().commit();
           return lockedAccount;
        });

        //then
        Thread.sleep(1000);
        try {
            Account shouldNotBeLocked = testSubject.lock(id);
            Assert.fail();
        } catch (Exception e) {
            //expect
            Assert.assertThat(e, CoreMatchers.instanceOf(IllegalStateException.class));
        }
    }

    private Account dummy(String name) {
        return new Account(name, new BigDecimal(100));
    }

    private Account saveAccount(Account toBeSaved) {
        try {
            return inSeparateThread(()->{
                em.get().getTransaction().begin();
                em.get().persist(toBeSaved);
                em.get().flush();
                em.get().getTransaction().commit();
                return  toBeSaved;
            }).get();
        } catch (InterruptedException|ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is just for emulation of separate transaction.
     * @param call call to be executed in separate thread
     * @param <T>
     * @return result of execution
     */
    private <T> Future<T> inSeparateThread(Callable<T> call){
            return executor.submit(call);
    }
}