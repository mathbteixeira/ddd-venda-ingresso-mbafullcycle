package com.fullcycle.ddd.infra.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity for storing ticket history entries.
 */
@Entity
@Table(name = "ingresso_historico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoHistoricoJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "ingresso_id")
    private UUID ingressoId;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private LocalDateTime data;
    
    public IngressoHistoricoJpaEntity(UUID ingressoId, String descricao, LocalDateTime data) {
        this.ingressoId = ingressoId;
        this.descricao = descricao;
        this.data = data;
    }
}