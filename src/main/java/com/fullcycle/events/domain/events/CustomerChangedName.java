package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Customer;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CustomerChangedName implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Customer.CustomerId aggregateId;
    private final String name;

    public CustomerChangedName(Customer.CustomerId aggregateId, String name) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.occurredOn = Instant.now();
    }
}