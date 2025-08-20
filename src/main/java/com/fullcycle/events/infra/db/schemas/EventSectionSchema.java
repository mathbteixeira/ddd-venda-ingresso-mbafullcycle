package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.EventSection;
import com.fullcycle.events.domain.entities.EventSection.EventSectionId;
import com.fullcycle.events.infra.db.types.EventIdSchemaType;
import com.fullcycle.events.infra.db.types.EventSectionIdSchemaType;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "event_sections")
public class EventSectionSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = EventSectionIdSchemaType.class)
    private EventSectionId id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished = false;

    @Column(name = "total_spots", nullable = false)
    private int totalSpots = 0;

    @Column(name = "total_spots_reserved", nullable = false)
    private int totalSpotsReserved = 0;

    @Column(name = "price", nullable = false)
    private double price = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @Convert(converter = EventIdSchemaType.class)
    private EventSchema event;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventSpotSchema> spots;

    protected EventSectionSchema() {}

    public EventSectionSchema(EventSectionId id, String name, String description, int totalSpots, double price, EventSchema event) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalSpots = totalSpots;
        this.price = price;
        this.event = event;
    }

    public EventSectionId getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isPublished() { return isPublished; }
    public int getTotalSpots() { return totalSpots; }
    public int getTotalSpotsReserved() { return totalSpotsReserved; }
    public double getPrice() { return price; }
    public EventSchema getEvent() { return event; }
    public List<EventSpotSchema> getSpots() { return spots; }

    public EventSection toDomain() {
        EventSection section = new EventSection(
                id,
                name,
                description,
                isPublished,
                totalSpots,
                totalSpotsReserved,
                price
        );

        if (spots != null) {
            spots.forEach(s -> section.getSpots().add(s.toDomain()));
        }

        return section;
    }
}