package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.EventSpot;
import com.fullcycle.events.domain.entities.EventSpot.EventSpotId;
import com.fullcycle.events.infra.db.types.EventSpotIdSchemaType;
import com.fullcycle.events.infra.db.types.EventSectionIdSchemaType;
import jakarta.persistence.*;

@Entity
@Table(name = "event_spots")
public class EventSpotSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = EventSpotIdSchemaType.class)
    private EventSpotId id;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved = false;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @Convert(converter = EventSectionIdSchemaType.class)
    private EventSectionSchema section;

    protected EventSpotSchema() {}

    public EventSpotSchema(EventSpotId id, String location, EventSectionSchema section) {
        this.id = id;
        this.location = location;
        this.section = section;
    }

    public EventSpotId getId() { return id; }
    public String getLocation() { return location; }
    public boolean isReserved() { return isReserved; }
    public boolean isPublished() { return isPublished; }
    public EventSectionSchema getSection() { return section; }

    public EventSpot toDomain() {
        return new EventSpot(
                id,
                location,
                isReserved,
                isPublished
        );
    }
}