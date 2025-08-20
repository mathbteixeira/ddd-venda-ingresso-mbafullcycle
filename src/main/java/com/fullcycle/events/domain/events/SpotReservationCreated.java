package com.fullcycle.events.domain.events;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.EventSpot;
import lombok.Getter;

import java.time.Instant;

@Getter
public class SpotReservationCreated implements IDomainEvent {

    private final int eventVersion = 1;
    private final Instant occurredOn;
    private final EventSpot.EventSpotId aggregateId;
    private final Instant reservationDate;
    private final Customer.CustomerId customerId;

    public SpotReservationCreated(EventSpot.EventSpotId aggregateId, Instant reservationDate, Customer.CustomerId customerId) {
        this.aggregateId = aggregateId;
        this.reservationDate = reservationDate;
        this.customerId = customerId;
        this.occurredOn = Instant.now();
    }
}