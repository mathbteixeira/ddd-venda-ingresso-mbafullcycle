package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.Partner.PartnerId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PartnerIdSchemaType implements AttributeConverter<PartnerId, String> {

    @Override
    public String convertToDatabaseColumn(PartnerId partnerId) {
        if (partnerId == null) {
            return null;
        }
        return partnerId.getValue();
    }

    @Override
    public PartnerId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new PartnerId(dbData);
    }
}