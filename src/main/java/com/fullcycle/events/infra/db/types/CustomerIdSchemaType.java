package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.Customer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerIdSchemaType implements AttributeConverter<Customer.CustomerId, String> {

    @Override
    public String convertToDatabaseColumn(Customer.CustomerId customerId) {
        if (customerId == null) {
            return null;
        }
        return customerId.getValue();
    }

    @Override
    public Customer.CustomerId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new Customer.CustomerId(dbData);
    }
}