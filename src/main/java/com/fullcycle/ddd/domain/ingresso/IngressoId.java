package com.fullcycle.ddd.domain.ingresso;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Value Object representing the unique identifier of a ticket.
 */
@Getter
@EqualsAndHashCode
public class IngressoId {
    private final UUID id;

    private IngressoId(UUID id) {
        this.id = id;
    }

    public static IngressoId generate() {
        return new IngressoId(UUID.randomUUID());
    }

    public static IngressoId of(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do ingresso não pode ser nulo");
        }
        return new IngressoId(id);
    }

    public static IngressoId of(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do ingresso não pode ser nulo ou vazio");
        }
        try {
            return new IngressoId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID do ingresso inválido: " + id, e);
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }
}