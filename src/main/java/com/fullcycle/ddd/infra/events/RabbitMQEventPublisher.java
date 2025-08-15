package com.fullcycle.ddd.infra.events;

import com.fullcycle.ddd.domain.events.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens for domain events and publishes them to RabbitMQ.
 */
@Component
public class RabbitMQEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "domain-events";

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void handleDomainEvent(DomainEvent event) {
        String routingKey = event.getEventType();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
    }
}