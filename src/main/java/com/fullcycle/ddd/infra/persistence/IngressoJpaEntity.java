package com.fullcycle.ddd.infra.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingressos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoJpaEntity {
    @Id
    private UUID id;
    
    @Column(name = "evento_id", nullable = false)
    private UUID eventoId;
    
    @Column(nullable = false)
    private BigDecimal preco;
    
    @Column(nullable = false)
    private boolean vendido;
    
    @Column(name = "data_venda")
    private LocalDateTime dataVenda;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ingresso_id")
    private List<IngressoHistoricoJpaEntity> historico = new ArrayList<>();
}