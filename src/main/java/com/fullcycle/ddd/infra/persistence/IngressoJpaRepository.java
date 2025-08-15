package com.fullcycle.ddd.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * JPA Repository for IngressoJpaEntity.
 */
public interface IngressoJpaRepository extends JpaRepository<IngressoJpaEntity, UUID> {
    
    List<IngressoJpaEntity> findByEventoId(UUID eventoId);
    
    List<IngressoJpaEntity> findByVendido(boolean vendido);
    
    @Query("SELECT i FROM IngressoJpaEntity i LEFT JOIN FETCH i.historico WHERE i.id = :id")
    IngressoJpaEntity findByIdWithHistorico(@Param("id") UUID id);
}