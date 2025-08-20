package com.fullcycle.infrastructure.uow;

import com.fullcycle.application.IUnitOfWork;
import com.fullcycle.domain.AggregateRoot;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class UnitOfWorkJpa implements IUnitOfWork {

    private final PlatformTransactionManager transactionManager;
    private final EntityManager entityManager;

    public UnitOfWorkJpa(PlatformTransactionManager transactionManager,
                         EntityManager entityManager) {
        this.transactionManager = transactionManager;
        this.entityManager = entityManager;
    }

    @Override
    public <T> T runTransaction(Callable<T> callback) throws Exception {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            T result = callback.call();
            transactionManager.commit(status);
            return result;
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    public void commit() {
        entityManager.flush();
    }

    @Override
    public void rollback() {
        entityManager.clear();
    }

    @Override
    public List<AggregateRoot> getAggregateRoots() {
        // não temos PersistStack no JPA, então isso precisaria ser implementado manualmente ou removido
        return Collections.emptyList();
    }

    @Override public void beginTransaction() {}
    @Override public void completeTransaction() {}
    @Override public void rollbackTransaction() {}
}