package com.fullcycle.events.application.controllers;

import com.fullcycle.events.application.dtos.EventDto;
import com.fullcycle.events.application.services.EventService;
import com.fullcycle.events.domain.entities.Event;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> list() throws Exception {
        List<Event> events = eventService.findEvents();
        if (!events.isEmpty()) {
            logSizeInBytes("events", events.get(0));
        }
        return events;
    }

    @PostMapping
    public Event create(@RequestBody EventDto body) throws Exception {
        return eventService.create(
                body.getName(),
                body.getDescription(),
                body.getDate(),
                body.getPartnerId()
        );
    }

    @PutMapping("/{eventId}/publish-all")
    public Event publish(@PathVariable("eventId") String eventId) throws Exception {
        return eventService.publishAll(eventId);
    }

    private static int getSizeInBytes(Object obj) {
        String str;
        if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = obj.toString(); // você pode usar uma biblioteca como Jackson para JSON real
        }
        return str.getBytes(StandardCharsets.UTF_8).length;
    }

    private static void logSizeInBytes(String description, Object obj) {
        int bytes = getSizeInBytes(obj);
        System.out.println(description + " is approximately " + bytes + " B");
    }
}