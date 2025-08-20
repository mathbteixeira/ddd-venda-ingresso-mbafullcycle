package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.Event;
import com.fullcycle.events.domain.entities.Event.EventId;
import com.fullcycle.events.domain.entities.Partner;
import com.fullcycle.events.infra.db.types.EventIdSchemaType;
import com.fullcycle.events.infra.db.types.PartnerIdSchemaType;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "events")
public class EventSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = EventIdSchemaType.class)
    private EventId id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished = false;

    @Column(name = "total_spots", nullable = false)
    private int totalSpots = 0;

    @Column(name = "total_spots_reserved", nullable = false)
    private int totalSpotsReserved = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    @Convert(converter = PartnerIdSchemaType.class)
    private Partner partner;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventSectionSchema> sections;

    protected EventSchema() {} // JPA

    public EventSchema(EventId id, String name, String description, Instant date, Partner partner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.partner = partner;
    }

    // Getters
    public EventId getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getDate() { return date; }
    public boolean isPublished() { return isPublished; }
    public int getTotalSpots() { return totalSpots; }
    public int getTotalSpotsReserved() { return totalSpotsReserved; }
    public Partner getPartner() { return partner; }
    public List<EventSectionSchema> getSections() { return sections; }

    // Converte para a entidade de domínio
    public Event toDomain() {
        Event event = new Event(
                id,
                name,
                description,
                date,
                isPublished,
                totalSpots,
                totalSpotsReserved,
                partner.getId()
        );

        if (sections != null) {
            sections.forEach(s -> event.getSections().add(s.toDomain()));
        }

        return event;
    }
}