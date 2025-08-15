package com.fullcycle.ddd.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for transferring ticket history data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoHistoricoDTO {
    private String descricao;
    private LocalDateTime data;
}