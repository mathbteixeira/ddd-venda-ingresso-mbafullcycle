package com.fullcycle.ddd.application.service;

import com.fullcycle.ddd.domain.aggregate.Order;
import com.fullcycle.ddd.domain.aggregate.OrderRepository;
import com.fullcycle.ddd.domain.aggregate.EventRepository;
import com.fullcycle.ddd.domain.event.DomainEventPublisher;
import com.fullcycle.ddd.domain.valueobject.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final EventRepository eventRepo;
    private final DomainEventPublisher publisher;

    public OrderService(OrderRepository orderRepo, EventRepository eventRepo, DomainEventPublisher publisher) {
        this.orderRepo = orderRepo;
        this.eventRepo = eventRepo;
        this.publisher = publisher;
    }

    public Order createOrder(EventId eventId, CustomerId customerId, List<SeatId> seats, Money total) {
        var event = eventRepo.get(eventId);
        event.ensureSeatsAvailable(seats);

        Order order = new Order(OrderId.newId(), eventId, customerId, seats, total);
        orderRepo.save(order);

        publisher.publish(order.pullDomainEvents());
        return order;
    }
}