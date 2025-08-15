package com.fullcycle.ddd.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoJpaEntity {
    @Id
    private UUID id;
    private String evento;
    private BigDecimal preco;
    private boolean vendido;
}