package com.fullcycle.domain.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Fábrica para criar ICollection
public class MyCollectionFactory {

    public static <T> ICollection<T> create() {
        List<T> internalList = new ArrayList<>();
        return createProxy(internalList);
    }

    public static <T> ICollection<T> createFrom(List<T> target) {
        return createProxy(target);
    }

    private static <T> ICollection<T> createProxy(List<T> target) {
        return new ICollection<T>() {

            @Override
            public Iterable<T> getItems() {
                return Collections.unmodifiableList(target);
            }

            @Override
            public void add(T item, T... items) {
                target.add(item);
                if (items != null) {
                    target.addAll(Arrays.asList(items));
                }
            }

            @Override
            public void remove(T item, T... items) {
                target.remove(item);
                if (items != null) {
                    for (T i : items) {
                        target.remove(i);
                    }
                }
            }

            @Override
            public Optional<T> find(Predicate<T> predicate) {
                return target.stream().filter(predicate).findFirst();
            }

            @Override
            public <U> List<U> map(Function<T, U> mapper) {
                return target.stream().map(mapper).collect(Collectors.toList());
            }

            @Override
            public void removeAll() {
                target.clear();
            }

            @Override
            public int count() {
                return target.size();
            }

            @Override
            public int size() {
                return target.size();
            }

            @Override
            public List<T> values() {
                return new ArrayList<>(target);
            }

            @Override
            public Iterator<T> iterator() {
                return target.iterator();
            }

            @Override
            public void forEach(Consumer<? super T> action) {
                target.forEach(action);
            }
        };
    }
}