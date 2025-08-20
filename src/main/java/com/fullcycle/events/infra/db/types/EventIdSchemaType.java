package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.Event;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventIdSchemaType implements AttributeConverter<Event.EventId, String> {

    @Override
    public String convertToDatabaseColumn(Event.EventId eventId) {
        if (eventId == null) {
            return null;
        }
        return eventId.getValue();
    }

    @Override
    public Event.EventId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new Event.EventId(dbData);
    }
}