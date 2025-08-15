package com.fullcycle.ddd.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for applying a discount to a ticket.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicarDescontoRequest {
    private BigDecimal percentual;
}