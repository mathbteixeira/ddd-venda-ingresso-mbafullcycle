package com.fullcycle.storedevents.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.shared.JsonUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StoredEvent extends AggregateRoot {

    // --------------------
    // Value Object de ID
    // --------------------
    public static class StoredEventId extends Uuid {
        public StoredEventId() {
            super();
        }
        public StoredEventId(String value) {
            super(value);
        }
    }

    // --------------------
    // Command
    // --------------------
    public static class StoredEventCommand {
        private final IDomainEvent domainEvent;
        private final Instant occurredOn;

        public StoredEventCommand(IDomainEvent domainEvent, Instant occurredOn) {
            this.domainEvent = domainEvent;
            this.occurredOn = occurredOn;
        }
        public IDomainEvent getDomainEvent() {
            return domainEvent;
        }
        public Instant getOccurredOn() {
            return occurredOn;
        }
    }

    // --------------------
    // Entity
    // --------------------
    private final StoredEventId id;
    private final String body;
    private final Instant occurredOn;
    private final String typeName;

    public StoredEvent(StoredEventId id, String body, Instant occurredOn, String typeName) {
        this.id = (id == null ? new StoredEventId() : id);
        this.body = body;
        this.occurredOn = occurredOn;
        this.typeName = typeName;
    }

    public static StoredEvent create(IDomainEvent domainEvent) {
        String json = JsonUtils.toJson(domainEvent);
        return new StoredEvent(
                new StoredEventId(),
                json,
                domainEvent.getOccurredOn(),
                domainEvent.getClass().getSimpleName()
        );
    }

    public StoredEventId getId() { return id; }
    public String getBody() { return body; }
    public Instant getOccurredOn() { return occurredOn; }
    public String getTypeName() { return typeName; }

    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.getValue());
        map.put("body", body);
        map.put("occurred_on", occurredOn);
        map.put("type_name", typeName);
        return map;
    }
}