package com.revolut.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.revolut.dao.AccountDao;
import com.revolut.dao.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;
import java.util.Objects;

@Transactional
public class AccountDaoJpaImpl implements AccountDao {

    private final Provider<EntityManager> em;

    @Inject
    public AccountDaoJpaImpl(Provider<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Account save(Account account) {
        em.get().persist(account);
        em.get().flush();
        return account;
    }

    @Override
    public Account findById(Long id) {
        return em.get().find(Account.class, id);
    }

    @Override
    public Account update(Account account) {
        account = em.get().merge(account);
        em.get().flush();
        return account;
    }

    @Override
    public Account deleteById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        Account existing = this.findById(id);
        if (Objects.isNull(existing)){
            return null;
        }
        em.get().remove(existing);
        em.get().flush();
        return existing;
    }

    @Override
    public Account lock(Long accountId) {
        try {
            return em.get().find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
        } catch (PessimisticLockException e) {
            throw new IllegalStateException("Account locked exception");
        }
    }
}
