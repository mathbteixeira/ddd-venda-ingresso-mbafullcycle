package com.fullcycle.ddd.domain.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Abstract base class for domain events.
 * Provides common functionality for all domain events.
 */
@Getter
public abstract class AbstractDomainEvent implements DomainEvent {
    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected AbstractDomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public String getEventType() {
        return this.getClass().getSimpleName();
    }
}