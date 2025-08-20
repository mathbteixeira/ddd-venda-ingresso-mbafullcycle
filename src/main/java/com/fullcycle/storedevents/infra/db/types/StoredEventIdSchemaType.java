package com.fullcycle.storedevents.infra.db.types;

import com.fullcycle.storedevents.domain.entities.StoredEvent.StoredEventId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StoredEventIdSchemaType implements AttributeConverter<StoredEventId, String> {

    @Override
    public String convertToDatabaseColumn(StoredEventId storedEventId) {
        if (storedEventId == null) {
            return null;
        }
        return storedEventId.getValue();
    }

    @Override
    public StoredEventId convertToEntityAttribute(String dbValue) {
        if (dbValue == null || dbValue.isEmpty()) {
            return null;
        }
        return new StoredEventId(dbValue);
    }
}