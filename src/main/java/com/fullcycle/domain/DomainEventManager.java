package com.fullcycle.domain;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DomainEventManager {

    private final Map<String, List<Consumer<IDomainEvent>>> domainEventSubscribers = new HashMap<>();
    private final Map<String, List<Consumer<IDomainEvent>>> integrationEventSubscribers = new HashMap<>();

    public void register(String event, Consumer<IDomainEvent> handler) {
        domainEventSubscribers
                .computeIfAbsent(event, k -> new ArrayList<>())
                .add(handler);
    }

    public void registerForIntegrationEvent(String event, Consumer<IDomainEvent> handler) {
        integrationEventSubscribers
                .computeIfAbsent(event, k -> new ArrayList<>())
                .add(handler);
    }

    public void publish(AggregateRoot aggregateRoot) {
        aggregateRoot.getEvents().forEach(event -> {
            String eventName = event.getClass().getSimpleName();
            List<Consumer<IDomainEvent>> handlers = domainEventSubscribers.get(eventName);
            if (handlers != null) {
                handlers.forEach(h -> h.accept(event));
            }
        });
    }

    public void publishForIntegrationEvent(AggregateRoot aggregateRoot) {
        aggregateRoot.getEvents().forEach(event -> {
            String eventName = event.getClass().getSimpleName();
            List<Consumer<IDomainEvent>> handlers = integrationEventSubscribers.get(eventName);
            if (handlers != null) {
                handlers.forEach(h -> h.accept(event));
            }
        });
    }
}