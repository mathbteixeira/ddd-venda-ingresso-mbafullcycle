package com.fullcycle.application;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.DomainEventManager;
import java.util.List;

public class ApplicationService {

    private final IUnitOfWork uow;
    private final DomainEventManager domainEventManager;

    public ApplicationService(IUnitOfWork uow, DomainEventManager domainEventManager) {
        this.uow = uow;
        this.domainEventManager = domainEventManager;
    }

    public void start() {
        // opcional: lógica ao iniciar
    }

    public void finish() throws Exception {
        List<AggregateRoot> aggregateRoots = uow.getAggregateRoots();
        for (AggregateRoot aggregateRoot : aggregateRoots) {
            domainEventManager.publish(aggregateRoot);
        }
        uow.commit();
        for (AggregateRoot aggregateRoot : aggregateRoots) {
            domainEventManager.publishForIntegrationEvent(aggregateRoot);
        }
    }

    public void fail() {
        // opcional: lógica ao falhar
    }

    public <T> T run(TransactionalCallback<T> callback) throws Exception {
        start();
        try {
            T result = callback.execute();
            finish();
            return result;
        } catch (Exception e) {
            fail();
            throw e;
        }
    }

    @FunctionalInterface
    public interface TransactionalCallback<T> {
        T execute() throws Exception;
    }
}