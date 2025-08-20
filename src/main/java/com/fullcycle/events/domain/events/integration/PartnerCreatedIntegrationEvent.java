package com.fullcycle.events.domain.events.integration;

import com.fullcycle.domain.integration.IIntegrationEvent;
import com.fullcycle.events.domain.events.PartnerCreated;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PartnerCreatedIntegrationEvent implements IIntegrationEvent {

    private final String eventName;
    private final Map<String, Object> payload;
    private final int eventVersion;
    private final Instant occurredOn;

    public PartnerCreatedIntegrationEvent(PartnerCreated domainEvent) {
        this.eventName = PartnerCreatedIntegrationEvent.class.getSimpleName();
        this.payload = new HashMap<>();
        this.payload.put("id", domainEvent.getAggregateId().getValue());
        this.payload.put("name", domainEvent.getName());
        this.eventVersion = 1;
        this.occurredOn = domainEvent.getOccurredOn();
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }

    @Override
    public int getEventVersion() {
        return eventVersion;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }
}