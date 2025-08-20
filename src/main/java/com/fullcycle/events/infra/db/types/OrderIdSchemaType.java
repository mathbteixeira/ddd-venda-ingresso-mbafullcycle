package com.fullcycle.events.infra.db.types;

import com.fullcycle.events.domain.entities.Order.OrderId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderIdSchemaType implements AttributeConverter<OrderId, String> {

    @Override
    public String convertToDatabaseColumn(OrderId orderId) {
        if (orderId == null) {
            return null;
        }
        return orderId.getValue();
    }

    @Override
    public OrderId convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return new OrderId(dbData);
    }
}