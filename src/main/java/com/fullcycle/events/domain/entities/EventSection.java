package com.fullcycle.events.domain.entities;


import com.fullcycle.domain.Entity;
import com.fullcycle.domain.valueobjects.Uuid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class EventSection extends Entity {

    public static class EventSectionId extends Uuid {
        public EventSectionId() { super(); }
        public EventSectionId(String value) { super(value); }
    }

    private EventSectionId id;
    private String name;
    private String description;
    private boolean isPublished;
    private int totalSpots;
    private int totalSpotsReserved;
    private double price;
    private List<EventSpot> spots;

    // Construtor privado
    public EventSection(EventSectionId id, String name, String description, boolean isPublished,
                         int totalSpots, int totalSpotsReserved, double price) {
        this.id = id != null ? id : new EventSectionId();
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.totalSpots = totalSpots;
        this.totalSpotsReserved = totalSpotsReserved;
        this.price = price;
        this.spots = new ArrayList<>();
    }

    public static EventSection create(String name, String description, int totalSpots, double price) {
        EventSection section = new EventSection(null, name, description, false, totalSpots, 0, price);
        section.initSpots();
        return section;
    }

    private void initSpots() {
        for (int i = 0; i < this.totalSpots; i++) {
            this.spots.add(EventSpot.create());
        }
    }

    public void changeName(String name) { this.name = name; }
    public void changeDescription(String description) { this.description = description; }
    public void changePrice(double price) { this.price = price; }

    public void changeLocation(EventSpot.EventSpotId spotId, String location) {
        EventSpot spot = findSpot(spotId).orElseThrow(() -> new RuntimeException("Spot not found"));
        spot.changeLocation(location);
    }

    public void publishAll() {
        this.publish();
        this.spots.forEach(EventSpot::publish);
    }

    public void publish() { this.isPublished = true; }
    public void unPublish() { this.isPublished = false; }

    public boolean allowReserveSpot(EventSpot.EventSpotId spotId) {
        if (!this.isPublished) return false;

        EventSpot spot = findSpot(spotId).orElseThrow(() -> new RuntimeException("Spot not found"));
        return !spot.isReserved() && spot.isPublished();
    }

    public void markSpotAsReserved(EventSpot.EventSpotId spotId) {
        EventSpot spot = findSpot(spotId).orElseThrow(() -> new RuntimeException("Spot not found"));
        if (spot.isReserved()) throw new RuntimeException("Spot already reserved");
        spot.markAsReserved();
    }

    private Optional<EventSpot> findSpot(EventSpot.EventSpotId spotId) {
        return this.spots.stream().filter(s -> s.getId().equals(spotId)).findFirst();
    }

    public Object toJson() {
        return spots.stream()
                .map(EventSpot::toJson)
                .collect(Collectors.toList());
    }
}