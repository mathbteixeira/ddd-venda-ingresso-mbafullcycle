package com.fullcycle.ddd.domain.evento;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Value Object representing the unique identifier of an Event.
 * In DDD, identifiers are often modeled as value objects.
 */
@Getter
@EqualsAndHashCode
public class EventoId {
    private final UUID id;

    private EventoId(UUID id) {
        this.id = id;
    }

    public static EventoId generate() {
        return new EventoId(UUID.randomUUID());
    }

    public static EventoId of(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo");
        }
        return new EventoId(id);
    }

    public static EventoId of(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo ou vazio");
        }
        try {
            return new EventoId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID do evento inválido: " + id, e);
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }
}