package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class EventPublishAll implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final boolean isPublished = true;
    private final Event.EventId aggregateId;
    private final List<EventSection.EventSectionId> sectionsId;

    public EventPublishAll(
            Event.EventId aggregateId,
            List<EventSection.EventSectionId> sectionsId
    ) {
        this.aggregateId = aggregateId;
        this.sectionsId = sectionsId;
        this.occurredOn = Instant.now();
    }
}