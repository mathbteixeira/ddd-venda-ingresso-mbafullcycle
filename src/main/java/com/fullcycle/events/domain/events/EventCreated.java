package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.Partner;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventCreated implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final String name;
    private final String description;
    private final Instant date;
    private final boolean isPublished;
    private final int totalSpots;
    private final int totalSpotsReserved;
    private final Partner.PartnerId partnerId;

    public EventCreated(
            Event.EventId aggregateId,
            String name,
            String description,
            Instant date,
            boolean isPublished,
            int totalSpots,
            int totalSpotsReserved,
            Partner.PartnerId partnerId
    ) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.isPublished = isPublished;
        this.totalSpots = totalSpots;
        this.totalSpotsReserved = totalSpotsReserved;
        this.partnerId = partnerId;
        this.occurredOn = Instant.now();
    }
}