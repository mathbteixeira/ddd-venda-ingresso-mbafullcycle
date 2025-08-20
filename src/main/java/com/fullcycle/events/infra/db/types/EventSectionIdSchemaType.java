package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.EventSection.EventSectionId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventSectionIdSchemaType implements AttributeConverter<EventSectionId, String> {

    @Override
    public String convertToDatabaseColumn(EventSectionId eventSectionId) {
        if (eventSectionId == null) {
            return null;
        }
        return eventSectionId.getValue();
    }

    @Override
    public EventSectionId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new EventSectionId(dbData);
    }
}