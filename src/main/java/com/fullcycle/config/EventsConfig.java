package com.fullcycle.config;

import com.fullcycle.domain.DomainEventManager;
import com.fullcycle.events.application.handlers.MyHandlerHandler;
import com.fullcycle.events.domain.repositories.IPartnerRepository;
import com.fullcycle.events.domain.events.PartnerCreated;
import com.fullcycle.events.domain.events.integration.PartnerCreatedIntegrationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfig {

    @Bean
    public MyHandlerHandler myHandlerHandler(IPartnerRepository partnerRepo, DomainEventManager manager) {
        return new MyHandlerHandler(partnerRepo, manager);
    }

    @Bean
    public void registerEvents(DomainEventManager manager, MyHandlerHandler handler, RabbitTemplate rabbitTemplate) {
        manager.register(PartnerCreated.class.getSimpleName(), event -> {
            if (event instanceof PartnerCreated partnerCreatedEvent) {
                handler.handle(partnerCreatedEvent);
                PartnerCreatedIntegrationEvent integrationEvent =
                        new PartnerCreatedIntegrationEvent(partnerCreatedEvent);
                rabbitTemplate.convertAndSend(
                        "amq.direct",
                        "events.fullcycle.com/PartnerCreated",
                        integrationEvent
                );
            } else {
                throw new IllegalArgumentException("Unexpected event type: " + event.getClass());
            }
        });
    }
}