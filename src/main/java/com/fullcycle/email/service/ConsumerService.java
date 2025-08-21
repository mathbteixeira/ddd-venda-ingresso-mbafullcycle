package com.fullcycle.email.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumerService {

    @RabbitListener(queues = "emails")
    public void handle(@Payload Map<String, Object> msg) {
        String eventName = (String) msg.get("event_name");

        // Aqui você pode tratar os diferentes tipos de eventos
        switch (eventName) {
            case "PartnerCreatedIntegrationEvent" -> {
                System.out.println("Consumindo PartnerCreatedIntegrationEvent: " + msg);
                // lógica para PartnerCreatedIntegrationEvent
            }
            case "PartnerUpdatedIntegrationEvent" -> {
                System.out.println("Consumindo PartnerUpdatedIntegrationEvent: " + msg);
                // lógica para PartnerUpdatedIntegrationEvent
            }
            default -> System.out.println("Evento não reconhecido: " + msg);
        }
    }
}