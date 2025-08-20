package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventChangedName implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final String name;

    public EventChangedName(Event.EventId aggregateId, String name) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.occurredOn = Instant.now();
    }
}