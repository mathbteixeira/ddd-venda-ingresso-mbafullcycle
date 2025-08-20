package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Order;
import lombok.Getter;

import java.time.Instant;

@Getter
public class OrderCancelled implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final Order.OrderId aggregateId;
    private final Order.OrderStatus status;

    public OrderCancelled(Order.OrderId aggregateId, Order.OrderStatus status) {
        this.aggregateId = aggregateId;
        this.status = status;
        this.occurredOn = Instant.now();
    }
}