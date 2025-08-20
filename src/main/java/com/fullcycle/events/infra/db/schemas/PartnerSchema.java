package com.fullcycle.events.infra.db.schemas;

import com.fullcycle.events.domain.entities.Partner;
import com.fullcycle.events.domain.entities.Partner.PartnerId;
import com.fullcycle.events.infra.db.types.PartnerIdSchemaType;
import jakarta.persistence.*;

@Entity
@Table(name = "partners")
public class PartnerSchema {

    @Id
    @Column(name = "id", length = 36)
    @Convert(converter = PartnerIdSchemaType.class)
    private PartnerId id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    protected PartnerSchema() {}

    public PartnerSchema(PartnerId id, String name) {
        this.id = id;
        this.name = name;
    }

    public PartnerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Partner toDomain() {
        return Partner.create(name);
    }
}