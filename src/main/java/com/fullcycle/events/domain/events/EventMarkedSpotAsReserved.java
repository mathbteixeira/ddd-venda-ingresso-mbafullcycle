package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import com.fullcycle.events.domain.entities.EventSpot;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventMarkedSpotAsReserved implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final boolean spotIsReserved = true;
    private final Event.EventId aggregateId;
    private final EventSection.EventSectionId sectionId;
    private final EventSpot.EventSpotId spotId;

    public EventMarkedSpotAsReserved(
            Event.EventId aggregateId,
            EventSection.EventSectionId sectionId,
            EventSpot.EventSpotId spotId
    ) {
        this.aggregateId = aggregateId;
        this.sectionId = sectionId;
        this.spotId = spotId;
        this.occurredOn = Instant.now();
    }
}