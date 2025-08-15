package com.fullcycle.ddd.domain.ingresso;

import com.fullcycle.ddd.domain.evento.EventoId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Ingresso aggregate.
 * Following DDD principles, repositories work with aggregates.
 */
public interface IngressoRepository {
    Optional<Ingresso> findById(IngressoId id);
    List<Ingresso> findByEventoId(EventoId eventoId);
    List<Ingresso> findByVendido(boolean vendido);
    Ingresso save(Ingresso ingresso);
    void delete(IngressoId id);
}