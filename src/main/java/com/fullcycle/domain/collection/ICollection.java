package com.fullcycle.domain.collection;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ICollection<T> extends Iterable<T> {

    Iterable<T> getItems();

    void add(T item, T... items);

    void remove(T item, T... items);

    Optional<T> find(Predicate<T> predicate);

    <U> List<U> map(Function<T, U> mapper);

    void removeAll();

    int count();

    int size();

    List<T> values();
}