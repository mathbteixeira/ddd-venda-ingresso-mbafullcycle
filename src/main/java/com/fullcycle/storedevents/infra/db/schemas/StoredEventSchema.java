package com.fullcycle.storedevents.infra.db.schemas;

import com.fullcycle.storedevents.domain.entities.StoredEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent.StoredEventId;
import com.fullcycle.storedevents.infra.db.types.StoredEventIdSchemaType;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stored_events")
public class StoredEventSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = StoredEventIdSchemaType.class)
    private StoredEventId id;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "type_name", length = 255, nullable = false)
    private String typeName;

    @Column(name = "occurred_on", nullable = false)
    private Instant occurredOn;

    protected StoredEventSchema() {}

    public StoredEventSchema(StoredEventId id, String body, String typeName, Instant occurredOn) {
        this.id = id;
        this.body = body;
        this.typeName = typeName;
        this.occurredOn = occurredOn;
    }

    public StoredEventId getId() { return id; }
    public String getBody() { return body; }
    public String getTypeName() { return typeName; }
    public Instant getOccurredOn() { return occurredOn; }

    public StoredEvent toDomain() {
        return new StoredEvent(id, body, occurredOn, typeName);
    }
}