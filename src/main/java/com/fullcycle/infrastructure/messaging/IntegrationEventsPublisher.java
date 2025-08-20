package com.fullcycle.infrastructure.messaging;

import com.fullcycle.domain.integration.IIntegrationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class IntegrationEventsPublisher {

    private final RabbitTemplate rabbitTemplate;

    public IntegrationEventsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(IIntegrationEvent event) {
        System.out.println("IntegrationEventsPublisher.publish: " + event);
        rabbitTemplate.convertAndSend(
                "amq.direct",
                event.getEventName(), // equivalente ao job.data.event_name
                event
        );
    }
}