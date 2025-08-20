package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.services.PartnerService;
import com.fullcycle.events.domain.entities.Partner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partners")
public class PartnersController {

    private final PartnerService partnerService;

    public PartnersController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public List<Partner> list() throws Exception {
        return partnerService.list();
    }

    @PostMapping
    public Partner create(@RequestBody PartnerCreateRequest body) throws Exception {
        return partnerService.create(body.getName());
    }

    // DTO para criar parceiro
    public static class PartnerCreateRequest {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}