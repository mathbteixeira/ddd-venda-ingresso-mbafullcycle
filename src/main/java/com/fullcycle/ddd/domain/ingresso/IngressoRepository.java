package com.fullcycle.ddd.domain.ingresso;

import java.util.Optional;
import java.util.UUID;

public interface IngressoRepository {
    Optional<Ingresso> findById(UUID id);
    Ingresso save(Ingresso ingresso);
}