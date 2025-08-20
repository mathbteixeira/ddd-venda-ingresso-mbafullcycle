package com.fullcycle.events.application.controllers;

import com.fullcycle.events.domain.entities.EventSpot;
import com.fullcycle.events.application.services.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/sections/{sectionId}/spots")
public class EventSpotsController {

    private final EventService eventService;

    public EventSpotsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventSpot> list(
            @PathVariable("eventId") String eventId,
            @PathVariable("sectionId") String sectionId
    ) throws Exception {
        return eventService.findSpots(eventId, sectionId);
    }

    @PutMapping("/{spotId}")
    public EventSpot update(
            @PathVariable("eventId") String eventId,
            @PathVariable("sectionId") String sectionId,
            @PathVariable("spotId") String spotId,
            @RequestBody LocationUpdateRequest body
    ) throws Exception {
        return eventService.updateLocation(eventId, sectionId, spotId, body.getLocation());
    }

    // DTO para o corpo da requisição de atualização de localização
    public static class LocationUpdateRequest {
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}