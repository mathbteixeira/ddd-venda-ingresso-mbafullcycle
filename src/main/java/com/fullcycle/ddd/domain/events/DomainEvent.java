package com.fullcycle.ddd.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base interface for all domain events.
 * In DDD, domain events represent something that happened in the domain that domain experts care about.
 */
public interface DomainEvent {
    /**
     * Gets the unique identifier for this event.
     */
    UUID getEventId();
    
    /**
     * Gets the timestamp when this event occurred.
     */
    LocalDateTime getOccurredOn();
    
    /**
     * Gets the type of this event.
     */
    String getEventType();
}