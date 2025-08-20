package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.services.EventService;
import com.fullcycle.events.domain.entities.EventSection;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/sections")
public class EventSectionsController {

    private final EventService eventService;

    public EventSectionsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventSection> list(@PathVariable("eventId") String eventId) throws Exception {
        return eventService.findSections(eventId);
    }

    @PostMapping
    public EventSection create(
            @PathVariable("eventId") String eventId,
            @RequestBody SectionCreateRequest body
    ) throws Exception {
        return eventService.addSection(eventId, body.getName(), body.getDescription(),
                body.getTotalSpots(), body.getPrice());
    }

    @PutMapping("/{sectionId}")
    public EventSection update(
            @PathVariable("eventId") String eventId,
            @PathVariable("sectionId") String sectionId,
            @RequestBody SectionUpdateRequest body
    ) throws Exception {
        return eventService.updateSection(eventId, sectionId, body.getName(), body.getDescription());
    }

    // DTOs para requisições
    public static class SectionCreateRequest {
        private String name;
        private String description;
        private int totalSpots;
        private double price;

        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getTotalSpots() { return totalSpots; }
        public double getPrice() { return price; }

        public void setName(String name) { this.name = name; }
        public void setDescription(String description) { this.description = description; }
        public void setTotalSpots(int totalSpots) { this.totalSpots = totalSpots; }
        public void setPrice(double price) { this.price = price; }
    }

    public static class SectionUpdateRequest {
        private String name;
        private String description;

        public String getName() { return name; }
        public String getDescription() { return description; }

        public void setName(String name) { this.name = name; }
        public void setDescription(String description) { this.description = description; }
    }
}