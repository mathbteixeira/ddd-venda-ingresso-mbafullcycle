package com.fullcycle.ddd.domain.ingresso;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Value Object representing a historical entry for a ticket.
 * Used to track changes to the ticket over time.
 */
@Getter
@EqualsAndHashCode
public class IngressoHistorico {
    private final String descricao;
    private final LocalDateTime data;

    public IngressoHistorico(String descricao, LocalDateTime data) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        this.descricao = descricao;
        this.data = data;
    }

    @Override
    public String toString() {
        return data + ": " + descricao;
    }
}