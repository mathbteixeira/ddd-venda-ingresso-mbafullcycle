package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import com.fullcycle.events.domain.entities.EventSpot;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventChangedSpotLocation implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final EventSection.EventSectionId sectionId;
    private final EventSpot.EventSpotId spotId;
    private final String location;

    public EventChangedSpotLocation(
            Event.EventId aggregateId,
            EventSection.EventSectionId sectionId,
            EventSpot.EventSpotId spotId,
            String location
    ) {
        this.aggregateId = aggregateId;
        this.sectionId = sectionId;
        this.spotId = spotId;
        this.location = location;
        this.occurredOn = Instant.now();
    }
}