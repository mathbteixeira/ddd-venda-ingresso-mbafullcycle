package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.events.SpotReservationChanged;
import com.fullcycle.events.domain.events.SpotReservationCreated;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SpotReservation extends AggregateRoot {

    public static class SpotReservationId extends Uuid {
        public SpotReservationId() { super(); }
        public SpotReservationId(String value) { super(value); }
    }

    private EventSpot.EventSpotId spotId;
    private Instant reservationDate;
    private Customer.CustomerId customerId;

    public SpotReservation(EventSpot.EventSpotId spotId, Customer.CustomerId customerId, Instant reservationDate) {
        this.spotId = spotId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
    }

    public static SpotReservation create(EventSpot.EventSpotId spotId, Customer.CustomerId customerId) {
        SpotReservation reservation = new SpotReservation(spotId, customerId, Instant.now());
        reservation.addEvent(new SpotReservationCreated(
                reservation.spotId,
                reservation.reservationDate,
                reservation.customerId
        ));
        return reservation;
    }

    public void changeReservation(Customer.CustomerId customerId) {
        this.customerId = customerId;
        this.reservationDate = Instant.now();
        this.addEvent(new SpotReservationChanged(
                this.spotId,
                this.reservationDate,
                this.customerId
        ));
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("spot_id", spotId.getValue());
        json.put("customer_id", customerId.getValue());
        json.put("reservation_date", reservationDate);
        return json;
    }
}