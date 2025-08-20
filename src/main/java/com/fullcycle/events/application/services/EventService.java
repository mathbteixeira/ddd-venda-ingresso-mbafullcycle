package com.fullcycle.events.application.services;

import com.fullcycle.application.IUnitOfWork;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.EventSection;
import com.fullcycle.events.domain.entities.EventSpot;
import com.fullcycle.events.domain.repositories.IEventRepository;
import com.fullcycle.events.domain.repositories.IPartnerRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {

    private final IEventRepository eventRepo;
    private final IPartnerRepository partnerRepo;
    private final IUnitOfWork uow;

    public EventService(IEventRepository eventRepo, IPartnerRepository partnerRepo, IUnitOfWork uow) {
        this.eventRepo = eventRepo;
        this.partnerRepo = partnerRepo;
        this.uow = uow;
    }

    public List<Event> findEvents() throws Exception {
        return eventRepo.findAll();
    }

    public List<EventSection> findSections(String eventId) throws Exception {
        Event event = eventRepo.findById(new Uuid(eventId))
                .orElseThrow(() -> new RuntimeException("Event not found"));
        // Converte Iterable para Stream e coleta em List
        return StreamSupport.stream(event.getSections().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Event create(String name, String description, Instant date, String partnerId) throws Exception {
        return uow.runTransaction(() -> {
            var partner = partnerRepo.findById(new Uuid(partnerId))
                    .orElseThrow(() -> new RuntimeException("Partner not found"));
            Event event = partner.initEvent(name, description, date);
            eventRepo.add(event);
            uow.commit();
            return event;
        });
    }

    public Event update(String id, String name, String description, Instant date) throws Exception {
        return uow.runTransaction(() -> {
            Event event = eventRepo.findById(new Uuid(id))
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            if (name != null) event.changeName(name);
            if (description != null) event.changeDescription(description);
            if (date != null) event.changeDate(date);
            eventRepo.add(event);
            uow.commit();
            return event;
        });
    }

    public EventSection addSection(String eventId, String name, String description, int totalSpots, double price) throws Exception {
        return uow.runTransaction(() -> {
            Event event = eventRepo.findById(new Uuid(eventId))
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            EventSection section = EventSection.create(name, description, totalSpots, price);
            event.addSection(section);

            eventRepo.add(event);
            uow.commit();

            return section;
        });
    }

    public EventSection updateSection(String eventId, String sectionId, String name, String description) throws Exception {
        return uow.runTransaction(() -> {
            Event event = eventRepo.findById(new Uuid(eventId))
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            EventSection.EventSectionId secId = new EventSection.EventSectionId(sectionId);
            EventSection section = event.getSections().find(s -> s.getId().equals(secId))
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            section.changeName(name);
            section.changeDescription(description);

            eventRepo.add(event);
            uow.commit();

            return section;
        });
    }

    public List<EventSpot> findSpots(String eventId, String sectionId) throws Exception {
        Event event = eventRepo.findById(new Uuid(eventId))
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventSection.EventSectionId secId = new EventSection.EventSectionId(sectionId);
        EventSection section = event.getSections().find(s -> s.getId().equals(secId))
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return section.getSpots();
    }

    public EventSpot updateLocation(String eventId, String sectionId, String spotId, String location) throws Exception {
        return uow.runTransaction(() -> {
            Event event = eventRepo.findById(new Uuid(eventId))
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            EventSection.EventSectionId secId = new EventSection.EventSectionId(sectionId);
            EventSpot.EventSpotId spId = new EventSpot.EventSpotId(spotId);

            event.changeLocation(secId, spId, location);
            eventRepo.add(event);

            uow.commit();

            EventSection section = event.getSections().find(s -> s.getId().equals(secId))
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            for (EventSpot spot : section.getSpots()) {
                if (spot.getId().equals(spId)) {
                    return spot;
                }
            }

            return null;
        });
    }

    public Event publishAll(String eventId) throws Exception {
        return uow.runTransaction(() -> {
            Event event = eventRepo.findById(new Uuid(eventId))
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            event.publishAll();
            eventRepo.add(event);
            uow.commit();
            return event;
        });
    }
}