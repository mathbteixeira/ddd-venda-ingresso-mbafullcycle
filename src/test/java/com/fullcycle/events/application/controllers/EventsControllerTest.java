package com.fullcycle.events.application.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EventsControllerTest {

    @Autowired
    private EventsController controller;

    @BeforeEach
    void setUp() {
        // O Spring injeta o controller automaticamente
    }

    @Test
    void shouldBeDefined() {
        assertNotNull(controller, "EventsController should be initialized by Spring");
    }
}