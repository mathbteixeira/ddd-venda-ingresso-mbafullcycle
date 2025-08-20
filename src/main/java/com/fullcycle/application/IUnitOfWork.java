package com.fullcycle.application;

import com.fullcycle.domain.AggregateRoot;
import java.util.List;
import java.util.concurrent.Callable;

public interface IUnitOfWork {

    void beginTransaction() throws Exception;

    void completeTransaction() throws Exception;

    void rollbackTransaction() throws Exception;

    <T> T runTransaction(Callable<T> callback) throws Exception;

    void commit() throws Exception;

    void rollback() throws Exception;

    List<AggregateRoot> getAggregateRoots();
}
