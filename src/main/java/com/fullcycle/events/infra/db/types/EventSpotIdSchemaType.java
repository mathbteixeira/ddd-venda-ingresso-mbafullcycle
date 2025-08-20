package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.EventSpot.EventSpotId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventSpotIdSchemaType implements AttributeConverter<EventSpotId, String> {

    @Override
    public String convertToDatabaseColumn(EventSpotId eventSpotId) {
        if (eventSpotId == null) {
            return null;
        }
        return eventSpotId.getValue();
    }

    @Override
    public EventSpotId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new EventSpotId(dbData);
    }
}