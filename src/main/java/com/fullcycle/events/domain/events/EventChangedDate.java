package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventChangedDate implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final Instant date;

    public EventChangedDate(Event.EventId aggregateId, Instant date) {
        this.aggregateId = aggregateId;
        this.date = date;
        this.occurredOn = Instant.now();
    }
}