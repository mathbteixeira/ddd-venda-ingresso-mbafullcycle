package com.fullcycle.domain;

import com.fullcycle.domain.valueobjects.ValueObject;
import java.time.Instant;

public interface IDomainEvent {

    ValueObject getAggregateId();

    Instant getOccurredOn();

    int getEventVersion();
}