package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventPublish implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final boolean isPublished = true;
    private final Event.EventId aggregateId;

    public EventPublish(Event.EventId aggregateId) {
        this.aggregateId = aggregateId;
        this.occurredOn = Instant.now();
    }
}