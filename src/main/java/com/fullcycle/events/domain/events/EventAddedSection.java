package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventAddedSection implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final String sectionName;
    private final String sectionDescription;
    private final int sectionTotalSpots;
    private final double sectionPrice;
    private final int eventTotalSpots;

    public EventAddedSection(Event.EventId aggregateId,
                             String sectionName,
                             String sectionDescription,
                             int sectionTotalSpots,
                             double sectionPrice,
                             int eventTotalSpots) {
        this.aggregateId = aggregateId;
        this.sectionName = sectionName;
        this.sectionDescription = sectionDescription;
        this.sectionTotalSpots = sectionTotalSpots;
        this.sectionPrice = sectionPrice;
        this.eventTotalSpots = eventTotalSpots;
        this.occurredOn = Instant.now();
    }
}