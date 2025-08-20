package com.fullcycle.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class AggregateRoot extends Entity {

    private final Set<IDomainEvent> events = new HashSet<>();

    public void addEvent(IDomainEvent event) {
        this.events.add(event);
    }

    public void clearEvents() {
        this.events.clear();
    }

    public Set<IDomainEvent> getEvents() {
        return events;
    }
}