package com.fullcycle.ddd.domain.events;

/**
 * Interface for publishing domain events.
 * This follows the Mediator pattern to decouple event publishers from event handlers.
 */
public interface DomainEventPublisher {
    /**
     * Publishes a domain event to all registered handlers.
     *
     * @param event The domain event to publish
     */
    void publish(DomainEvent event);
}