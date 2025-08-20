package com.fullcycle.events.application.services;

import com.fullcycle.application.IUnitOfWork;
import com.fullcycle.events.application.gateways.PaymentGateway;
import com.fullcycle.events.domain.entities.*;
import com.fullcycle.events.domain.repositories.ICustomerRepository;
import com.fullcycle.events.domain.repositories.IEventRepository;
import com.fullcycle.events.domain.repositories.IOrderRepository;
import com.fullcycle.events.domain.repositories.ISpotReservationRepository;

import java.util.Optional;

public class OrderService {

    private final IOrderRepository orderRepo;
    private final ICustomerRepository customerRepo;
    private final IEventRepository eventRepo;
    private final ISpotReservationRepository spotReservationRepo;
    private final IUnitOfWork uow;
    private final PaymentGateway paymentGateway;

    public OrderService(
            IOrderRepository orderRepo,
            ICustomerRepository customerRepo,
            IEventRepository eventRepo,
            ISpotReservationRepository spotReservationRepo,
            IUnitOfWork uow,
            PaymentGateway paymentGateway
    ) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.eventRepo = eventRepo;
        this.spotReservationRepo = spotReservationRepo;
        this.uow = uow;
        this.paymentGateway = paymentGateway;
    }

    public Iterable<Order> list() throws Exception {
        return orderRepo.findAll();
    }

    public Order create(String eventId, String sectionId, String spotId, String customerId, String cardToken) throws Exception {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventSection.EventSectionId sectionUUID = new EventSection.EventSectionId(sectionId);
        EventSpot.EventSpotId spotUUID = new EventSpot.EventSpotId(spotId);

        if (!event.allowReserveSpot(sectionUUID, spotUUID)) {
            throw new RuntimeException("Spot not available");
        }

        Optional<SpotReservation> spotReservationOpt = spotReservationRepo.findById(spotUUID);
        if (spotReservationOpt.isPresent()) {
            throw new RuntimeException("Spot already reserved");
        }

        return uow.runTransaction(() -> {
            SpotReservation spotReservation = SpotReservation.create(spotUUID, customer.getId());
            spotReservationRepo.add(spotReservation);
            uow.commit();

            EventSection section = event.getSections().find(s -> s.getId().equals(sectionUUID))
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            try {
                paymentGateway.payment(cardToken, section.getPrice());

                Order order = Order.create(customer.getId(), spotUUID, section.getPrice());
                order.pay();
                orderRepo.add(order);

                event.markSpotAsReserved(sectionUUID, spotUUID);
                eventRepo.add(event);

                uow.commit();
                return order;
            } catch (Exception e) {
                Order order = Order.create(customer.getId(), spotUUID, section.getPrice());
                order.cancel();
                orderRepo.add(order);
                uow.commit();
                throw new RuntimeException("Aconteceu um erro reservar o seu lugar");
            }
        });
    }
}