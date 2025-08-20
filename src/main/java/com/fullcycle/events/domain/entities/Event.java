package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.collection.ICollection;
import com.fullcycle.domain.collection.MyCollectionFactory;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.events.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event extends AggregateRoot {

    public static class EventId extends Uuid {
        public EventId() { super(); }
        public EventId(String value) { super(value); }
    }

    private EventId id;
    private String name;
    private String description;
    private Instant date;
    private boolean isPublished;
    private int totalSpots;
    private int totalSpotsReserved;
    private Partner.PartnerId partnerId;
    private ICollection<EventSection> sections;

    public Event(EventId id, String name, String description, Instant date, boolean isPublished,
                 int totalSpots, int totalSpotsReserved, Partner.PartnerId partnerId) {
        this.id = id != null ? id : new EventId();
        this.name = name;
        this.description = description;
        this.date = date;
        this.isPublished = isPublished;
        this.totalSpots = totalSpots;
        this.totalSpotsReserved = totalSpotsReserved;
        this.partnerId = partnerId;
        this.sections = MyCollectionFactory.create();
    }

    public static Event create(String name, String description, Instant date, Partner.PartnerId partnerId) {
        Event event = new Event(null, name, description, date, false, 0, 0, partnerId);
        event.addEvent(new EventCreated(event.id, name, description, date, false, 0, 0, partnerId));
        return event;
    }

    public void changeName(String name) {
        this.name = name;
        this.addEvent(new EventChangedName(this.id, name));
    }

    public void changeDescription(String description) {
        this.description = description;
        this.addEvent(new EventChangedDescription(this.id, description));
    }

    public void changeDate(Instant date) {
        this.date = date;
        this.addEvent(new EventChangedDate(this.id, date));
    }

    public void publish() {
        this.isPublished = true;
        this.addEvent(new EventPublish(this.id));
    }

    public void unPublish() {
        this.isPublished = false;
        this.addEvent(new EventUnpublish(this.id));
    }

    public void publishAll() {
        this.publish();
        this.sections.forEach(EventSection::publishAll);

        List<EventSection.EventSectionId> sectionIds = new ArrayList<>();
        for (EventSection section : sections) {
            sectionIds.add(section.getId());
        }

        this.addEvent(new EventPublishAll(this.id, sectionIds));
    }

    public void addSection(EventSection section) {
        sections.add(section);
        this.totalSpots += section.getTotalSpots();
        this.addEvent(new EventAddedSection(this.id, section.getName(), section.getDescription(),
                section.getTotalSpots(), section.getPrice(), this.totalSpots));
    }

    public boolean allowReserveSpot(EventSection.EventSectionId sectionId, EventSpot.EventSpotId spotId) {
        if (!this.isPublished) return false;
        EventSection section = sections.find(s -> s.getId().equals(sectionId))
                .orElseThrow(() -> new RuntimeException("Section not found"));
        return section.allowReserveSpot(spotId);
    }

    public void markSpotAsReserved(EventSection.EventSectionId sectionId, EventSpot.EventSpotId spotId) {
        EventSection section = sections.find(s -> s.getId().equals(sectionId))
                .orElseThrow(() -> new RuntimeException("Section not found"));
        section.markSpotAsReserved(spotId);
        this.addEvent(new EventMarkedSpotAsReserved(this.id, sectionId, spotId));
    }

    public void changeSectionInformation(EventSection.EventSectionId sectionId,
                                         String name,
                                         String description) {
        EventSection section = sections.find(s -> s.getId().equals(sectionId))
                .orElseThrow(() -> new RuntimeException("Section not found"));
        if (name != null) {
            section.changeName(name);
        }
        if (description != null) {
            section.changeDescription(description);
        }
        this.addEvent(
                new EventChangedSection(this.id, sectionId, name, description)
        );
    }

    public void changeLocation(EventSection.EventSectionId sectionId, EventSpot.EventSpotId spotId, String location) {
        EventSection section = sections.find(s -> s.getId().equals(sectionId))
                .orElseThrow(() -> new RuntimeException("Section not found"));

        section.changeLocation(spotId, location);

        this.addEvent(new EventChangedSpotLocation(this.id, sectionId, spotId, location));
    }

    public ICollection<EventSection> getSections() { return sections; }
    public void setSections(ICollection<EventSection> sections) { this.sections = sections; }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id.getValue());
        json.put("name", name);
        json.put("description", description);
        json.put("date", date);
        json.put("is_published", isPublished);
        json.put("total_spots", totalSpots);
        json.put("total_spots_reserved", totalSpotsReserved);
        json.put("partner_id", partnerId.getValue());

        List<Object> sectionList = new ArrayList<>();
        for (EventSection section : sections) {
            sectionList.add(section.toJson());
        }
        json.put("sections", sectionList);

        return json;
    }
}