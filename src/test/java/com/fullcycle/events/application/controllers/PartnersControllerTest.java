package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.services.PartnerService;
import com.fullcycle.events.domain.entities.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnersControllerTest {

    private PartnerService partnerService;
    private PartnersController controller;

    @BeforeEach
    void setUp() {
        partnerService = mock(PartnerService.class);
        controller = new PartnersController(partnerService);
    }

    @Test
    void shouldBeDefined() {
        assertNotNull(controller);
    }

    @Test
    void listShouldReturnPartners() throws Exception {
        Partner partner = new Partner("Partner 1");
        when(partnerService.list()).thenReturn(Collections.singletonList(partner));

        List<Partner> result = controller.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Partner 1", result.get(0).getName());
        verify(partnerService, times(1)).list();
    }

    @Test
    void createShouldReturnPartner() throws Exception {
        Partner partner = new Partner("Partner 2");
        when(partnerService.create("Partner 2")).thenReturn(partner);

        PartnersController.PartnerCreateRequest request = new PartnersController.PartnerCreateRequest();
        request.setName("Partner 2");

        Partner result = controller.create(request);

        assertNotNull(result);
        assertEquals("Partner 2", result.getName());
        verify(partnerService, times(1)).create("Partner 2");
    }
}