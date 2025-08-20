package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.Order;
import com.fullcycle.events.domain.entities.Order.OrderId;
import com.fullcycle.events.domain.entities.Order.OrderStatus;
import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.Customer.CustomerId;
import com.fullcycle.events.domain.entities.EventSpot;
import com.fullcycle.events.domain.entities.EventSpot.EventSpotId;
import com.fullcycle.events.infra.db.types.CustomerIdSchemaType;
import com.fullcycle.events.infra.db.types.EventSpotIdSchemaType;
import com.fullcycle.events.infra.db.types.OrderIdSchemaType;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = OrderIdSchemaType.class)
    private OrderId id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @Convert(converter = CustomerIdSchemaType.class)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_spot_id", nullable = false)
    @Convert(converter = EventSpotIdSchemaType.class)
    private EventSpot eventSpot;

    protected OrderSchema() {}

    public OrderSchema(OrderId id, double amount, OrderStatus status, Customer customer, EventSpot eventSpot) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.customer = customer;
        this.eventSpot = eventSpot;
    }

    public OrderId getId() { return id; }
    public double getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public EventSpot getEventSpot() { return eventSpot; }

    public Order toDomain() {
        return new Order(
                id,
                new CustomerId(customer.getId().getValue()),
                new EventSpotId(eventSpot.getId().getValue()),
                amount
        );
    }
}