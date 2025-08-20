package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.SpotReservation;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.Customer.CustomerId;
import com.fullcycle.events.domain.entities.EventSpot.EventSpotId;
import com.fullcycle.events.infra.db.types.CustomerIdSchemaType;
import com.fullcycle.events.infra.db.types.EventSpotIdSchemaType;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "spot_reservations")
public class SpotReservationSchema {

    @Id
    @Column(name = "spot_id", length = 36)
    @Convert(converter = EventSpotIdSchemaType.class)
    private EventSpotId spotId;

    @Column(name = "reservation_date", nullable = false)
    private Instant reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @Convert(converter = CustomerIdSchemaType.class)
    private Customer customer;

    protected SpotReservationSchema() {}

    public SpotReservationSchema(EventSpotId spotId, Instant reservationDate, Customer customer) {
        this.spotId = spotId;
        this.reservationDate = reservationDate;
        this.customer = customer;
    }

    public EventSpotId getSpotId() { return spotId; }
    public Instant getReservationDate() { return reservationDate; }
    public Customer getCustomer() { return customer; }

    public SpotReservation toDomain() {
        return new SpotReservation(
                spotId,
                new CustomerId(customer.getId().getValue()),
                reservationDate
        );
    }
}