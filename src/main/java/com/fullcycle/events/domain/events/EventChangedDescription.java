package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventChangedDescription implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final String description;

    public EventChangedDescription(Event.EventId aggregateId, String description) {
        this.aggregateId = aggregateId;
        this.description = description;
        this.occurredOn = Instant.now();
    }
}