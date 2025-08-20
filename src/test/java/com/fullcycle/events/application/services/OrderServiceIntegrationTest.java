package com.fullcycle.events.application.services;

import com.fullcycle.events.domain.entities.Customer;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import com.fullcycle.events.domain.entities.Partner;
import com.fullcycle.events.domain.repositories.*;
import com.fullcycle.infrastructure.uow.UnitOfWorkJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.util.Date;

@DataJpaTest
@Import({
        OrderService.class,
        UnitOfWorkJpa.class,
        CustomerService.class,
        EventService.class,
        PartnerService.class,
        com.fullcycle.events.infra.db.repositories.CustomerMySqlRepository.class,
        com.fullcycle.events.infra.db.repositories.EventMysqlRepository.class,
        com.fullcycle.events.infra.db.repositories.PartnerMysqlRepository.class,
        com.fullcycle.events.infra.db.repositories.OrderMysqlRepository.class,
        com.fullcycle.events.infra.db.repositories.SpotReservationMysqlRepository.class
})
class OrderServiceIntegrationTest {

    @Autowired
    private ICustomerRepository customerRepo;

    @Autowired
    private IPartnerRepository partnerRepo;

    @Autowired
    private IEventRepository eventRepo;

    @Autowired
    private IOrderRepository orderRepo;

    @Autowired
    private ISpotReservationRepository spotReservationRepo;

    @Autowired
    private UnitOfWorkJpa uow;

    @Autowired
    private OrderService orderService;

    @Test
    void deveCriarUmaOrder() throws Exception {
        // --- persistência inicial ---
        Customer customer = Customer.create("Customer 1", "70375887091");
        customerRepo.add(customer);

        Partner partner = Partner.create("Partner 1");
        partnerRepo.add(partner);

        Event event = partner.initEvent("Event 1", "Event 1", new Date().toInstant());
        event.addSection(EventSection.create("Section 1", "Section 1", 100, 1000));
        event.publishAll();
        eventRepo.add(event);

        uow.commit(); // força flush

        // pegar INFO da Section e Spot
        EventSection section = event.getSections().iterator().next();
        String sectionId = section.getId().getValue();
        String spotId = section.getSpots().get(0).getId().getValue();

        // --- execução
        try {
            orderService.create(
                    event.getIdValue().toString(),
                    sectionId,
                    customer.getId().getValue(),
                    spotId,
                    "TOKEN-FAKE"
            );

            orderService.create(
                    event.getIdValue().toString(),
                    sectionId,
                    customer.getId().getValue(),
                    spotId,
                    "TOKEN-FAKE"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(orderRepo.findAll());
            System.out.println(spotReservationRepo.findAll());
        }
    }
}