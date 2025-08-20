package com.fullcycle.events.domain.entities;

import com.fullcycle.domain.AggregateRoot;
import com.fullcycle.domain.valueobjects.Uuid;
import com.fullcycle.events.domain.events.PartnerChangedName;
import com.fullcycle.events.domain.events.PartnerCreated;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Partner extends AggregateRoot {

    public static class PartnerId extends Uuid {
        public PartnerId() { super(); }
        public PartnerId(String value) { super(value); }
    }

    private PartnerId id;
    private String name;

    public Partner(String name) {
        this(null, name);
    }

    public Partner(PartnerId id, String name) {
        this.id = id != null ? id : new PartnerId();
        this.name = name;
    }

    public static Partner create(String name) {
        Partner partner = new Partner(name);
        partner.addEvent(new PartnerCreated(partner.id, partner.name));
        return partner;
    }

    public Event initEvent(String name, String description, Instant date) {
        return Event.create(name, description, date, this.id);
    }

    public void changeName(String name) {
        this.name = name;
        this.addEvent(new PartnerChangedName(this.id, this.name));
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id.getValue());
        json.put("name", name);
        return json;
    }
}