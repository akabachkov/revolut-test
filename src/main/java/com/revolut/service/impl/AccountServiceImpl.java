package com.revolut.service.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.revolut.dao.AccountDao;
import com.revolut.dao.model.Account;
import com.revolut.service.AccountService;
import com.revolut.service.exception.AccountLockedException;
import com.revolut.service.exception.AccountNotFoundException;
import com.revolut.service.exception.InsufficientFundsAccountException;
import com.revolut.web.dto.AccountDTO;
import com.revolut.web.dto.TransactionResultDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountDao dao;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO account) {
        if (Objects.isNull(account)) {
            throw new IllegalArgumentException("account details should be provided");
        }
        Account saved = dao.save(mapDtoToModel(account));
        return mapModelToDto(saved);
    }

    @Override
    @Transactional
    public AccountDTO deleteAccount(Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new IllegalArgumentException("account identifier should be provided");
        }
        Account deleted = dao.deleteById(accountId);
        if (Objects.isNull(deleted)) {
            throw new AccountNotFoundException(accountId);
        }
        return mapModelToDto(deleted);
    }

    @Override
    public AccountDTO findAccount(Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new IllegalArgumentException("account identifier should be provided");
        }
        Account account = dao.findById(accountId);
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException(accountId);
        }
        return mapModelToDto(account);
    }

    @Override
    @Transactional
    public TransactionResultDTO makeTransaction(Long creditAccountId, Long debitAccountId, BigDecimal amount) {
        if (Objects.isNull(creditAccountId) || Objects.isNull(debitAccountId)) {
            throw new IllegalArgumentException("account identifiers should be provided");
        }
        if (Objects.equals(creditAccountId, debitAccountId)) {
            throw new IllegalArgumentException("different accounts should be in transaction");
        }
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("positive amount should be provided");
        }
        //say no to deadlocks
        Long minId = Math.min(creditAccountId, debitAccountId);
        Long maxId = Math.max(creditAccountId, debitAccountId);
        Account debitAccount = null;
        Account creditAccount = null;
        //lock in order from min to max by id
        Account lockedMin = lockAccount(minId);
        Account lockedMax = lockAccount(maxId);
        if (lockedMin.getId().equals(debitAccountId)) {
            debitAccount = lockedMin;
            creditAccount = lockedMax;
        } else {
            creditAccount = lockedMin;
            debitAccount = lockedMax;
        }

        if (creditAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsAccountException(creditAccount.getId(), creditAccount.getBalance());
        }
        //changing balance
        creditAccount.setBalance(creditAccount.getBalance().subtract(amount));
        debitAccount.setBalance(debitAccount.getBalance().add(amount));
        //saving to storage
        dao.update(creditAccount);
        dao.update(debitAccount);

        return new TransactionResultDTO(mapModelToDto(debitAccount),mapModelToDto(creditAccount));
    }

    @NotNull
    private Account lockAccount(Long id) {
        try {
            Account locked = dao.lock(id);
            if (Objects.isNull(locked)) {
                throw new AccountNotFoundException(id);
            }
            return locked;
        } catch (IllegalStateException e) {
            throw new AccountLockedException(id);
        }
    }

    //TODO use mapstruct instead of manual mapping
    private AccountDTO mapModelToDto(Account model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return new AccountDTO(model.getId(), model.getHolder(), model.getBalance());
    }

    private Account mapDtoToModel(AccountDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return new Account(dto.getHolder(), dto.getBalance());
    }

}
