package com.fullcycle.domain.repository;

import com.fullcycle.domain.AggregateRoot;
import java.util.List;
import java.util.Optional;

public interface IRepository<E extends AggregateRoot> {

    void add(E entity) throws Exception;

    Optional<E> findById(Object id) throws Exception;

    List<E> findAll() throws Exception;

    void delete(E entity) throws Exception;
}