package com.fullcycle.events.infra.db.types;

import com.fullcycle.domain.valueobjects.Cpf;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CpfSchemaType implements AttributeConverter<Cpf, String> {

    @Override
    public String convertToDatabaseColumn(Cpf cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.getValue();
    }

    @Override
    public Cpf convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new Cpf(dbData);
    }
}