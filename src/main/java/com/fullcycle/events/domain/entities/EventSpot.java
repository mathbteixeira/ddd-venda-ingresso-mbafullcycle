package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.Entity;
import com.fullcycle.domain.valueobjects.Uuid;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EventSpot extends Entity {

    public static class EventSpotId extends Uuid {
        public EventSpotId() { super(); }
        public EventSpotId(String value) { super(value); }
    }

    private EventSpotId id;
    private String location;
    private boolean isReserved;
    private boolean isPublished;

    public EventSpot(EventSpotId id, String location, boolean isReserved, boolean isPublished) {
        super();
        this.id = id != null ? id : new EventSpotId();
        this.location = location;
        this.isReserved = isReserved;
        this.isPublished = isPublished;
    }

    public static EventSpot create() {
        return new EventSpot(null, null, false, false);
    }

    public void changeLocation(String location) { this.location = location; }
    public void publish() { this.isPublished = true; }
    public void unPublish() { this.isPublished = false; }
    public void markAsReserved() { this.isReserved = true; }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id.getValue());
        json.put("location", location);
        json.put("reserved", isReserved);
        json.put("is_published", isPublished);
        return json;
    }
}