package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.EventSpot;
import com.fullcycle.events.domain.entities.Order;
import lombok.Getter;

import java.time.Instant;

@Getter
public class OrderCreated implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Order.OrderId aggregateId;
    private final Customer.CustomerId customerId;
    private final double amount;
    private final EventSpot.EventSpotId eventSpotId;
    private final Order.OrderStatus status;

    public OrderCreated(
            Order.OrderId aggregateId,
            Customer.CustomerId customerId,
            double amount,
            EventSpot.EventSpotId eventSpotId,
            Order.OrderStatus status
    ) {
        this.aggregateId = aggregateId;
        this.customerId = customerId;
        this.amount = amount;
        this.eventSpotId = eventSpotId;
        this.status = status;
        this.occurredOn = Instant.now();
    }
}