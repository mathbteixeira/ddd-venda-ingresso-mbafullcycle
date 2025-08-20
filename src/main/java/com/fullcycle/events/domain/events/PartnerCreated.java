package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Partner;
import lombok.Getter;

import java.time.Instant;

@Getter
public class PartnerCreated implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Partner.PartnerId aggregateId;
    private final String name;

    public PartnerCreated(Partner.PartnerId aggregateId, String name) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.occurredOn = Instant.now();
    }
}