package com.fullcycle.ddd.domain.ingresso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {
    private UUID id;
    private String evento;
    private BigDecimal preco;
    private boolean vendido;
}