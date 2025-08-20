package com.fullcycle.domain.integration;

import java.time.Instant;

public interface IIntegrationEvent<T> {

    String getEventName();

    T getPayload();

    int getEventVersion();

    Instant getOccurredOn();
}