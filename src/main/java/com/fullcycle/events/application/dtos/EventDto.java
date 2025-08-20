package com.fullcycle.events.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public class EventDto {

    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant date;

    private String partnerId;

    public EventDto() {}

    public EventDto(String name, String description, Instant date, String partnerId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}