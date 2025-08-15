package com.fullcycle.ddd.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request DTO for creating a new ticket.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarIngressoRequest {
    private UUID eventoId;
    private BigDecimal preco;
}