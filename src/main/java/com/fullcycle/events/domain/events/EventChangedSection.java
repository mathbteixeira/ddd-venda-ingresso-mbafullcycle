package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EventChangedSection implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Event.EventId aggregateId;
    private final EventSection.EventSectionId sectionId;
    private final String sectionName;
    private final String sectionDescription;

    public EventChangedSection(
            Event.EventId aggregateId,
            EventSection.EventSectionId sectionId,
            String sectionName,
            String sectionDescription
    ) {
        this.aggregateId = aggregateId;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.sectionDescription = sectionDescription;
        this.occurredOn = Instant.now();
    }
}