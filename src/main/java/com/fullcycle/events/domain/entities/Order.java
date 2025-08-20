package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.events.OrderCancelled;
import com.fullcycle.events.domain.events.OrderCreated;
import com.fullcycle.events.domain.events.OrderPaid;

public class Order extends AggregateRoot {

    public enum OrderStatus {
        PENDING,
        PAID,
        CANCELLED
    }

    public static class OrderId extends Uuid {
        public OrderId() { super(); }
        public OrderId(String value) { super(value); }
    }

    private OrderId id;
    private Customer.CustomerId customerId;
    private EventSpot.EventSpotId eventSpotId;
    private double amount;
    private OrderStatus status = OrderStatus.PENDING;

    public Order(OrderId id, Customer.CustomerId customerId, EventSpot.EventSpotId eventSpotId, double amount) {
        this.id = id != null ? id : new OrderId();
        this.customerId = customerId;
        this.eventSpotId = eventSpotId;
        this.amount = amount;
    }

    public static Order create(Customer.CustomerId customerId, EventSpot.EventSpotId eventSpotId, double amount) {
        Order order = new Order(null, customerId, eventSpotId, amount);
        order.addEvent(new OrderCreated(order.id, customerId, amount, eventSpotId, order.status));
        return order;
    }

    public void pay() {
        this.status = OrderStatus.PAID;
        this.addEvent(new OrderPaid(this.id, this.status));
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        this.addEvent(new OrderCancelled(this.id, this.status));
    }

    public OrderId getId() { return id; }
    public Customer.CustomerId getCustomerId() { return customerId; }
    public EventSpot.EventSpotId getEventSpotId() { return eventSpotId; }
    public double getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }

    public java.util.Map<String, Object> toJson() {
        java.util.Map<String, Object> json = new java.util.HashMap<>();
        json.put("id", id.getValue());
        json.put("customer_id", customerId.getValue());
        json.put("event_spot_id", eventSpotId.getValue());
        json.put("amount", amount);
        json.put("status", status.name());
        return json;
    }
}