package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.domain.valueobjects.Cpf;
import com.fullcycle.events.domain.entities.Customer;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CustomerCreated implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Customer.CustomerId aggregateId;
    private final String name;
    private final Cpf cpf;

    public CustomerCreated(Customer.CustomerId aggregateId, String name, Cpf cpf) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.cpf = cpf;
        this.occurredOn = Instant.now();
    }
}