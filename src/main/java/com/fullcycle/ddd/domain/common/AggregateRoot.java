package com.fullcycle.ddd.domain.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private final List<Object> domainEvents = new ArrayList<>();

    protected void addDomainEvent(Object event) {
        domainEvents.add(event);
    }

    protected void removeDomainEvent(Object event) {
        domainEvents.remove(event);
    }

    public List<Object> pullDomainEvents() {
        List<Object> eventsCopy = List.copyOf(domainEvents);
        domainEvents.clear();
        return eventsCopy;
    }
}