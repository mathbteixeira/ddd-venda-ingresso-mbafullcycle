package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.services.OrderService;
import com.fullcycle.events.domain.entities.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events/{eventId}/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Iterable<Order> list() throws Exception {
        return orderService.list();
    }

    @PostMapping
    public Order create(
            @PathVariable("eventId") String eventId,
            @RequestBody OrderCreateRequest body
    ) throws Exception {
        return orderService.create(
                eventId,
                body.getSectionId(),
                body.getSpotId(),
                body.getCustomerId(),
                body.getCardToken()
        );
    }

    // DTO para receber os dados do request body
    public static class OrderCreateRequest {
        private String sectionId;
        private String spotId;
        private String customerId;
        private String cardToken;

        // getters e setters
        public String getSectionId() { return sectionId; }
        public void setSectionId(String sectionId) { this.sectionId = sectionId; }

        public String getSpotId() { return spotId; }
        public void setSpotId(String spotId) { this.spotId = spotId; }

        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }

        public String getCardToken() { return cardToken; }
        public void setCardToken(String cardToken) { this.cardToken = cardToken; }
    }
}