package com.fullcycle.ddd.interfaces.dto;

import com.fullcycle.ddd.domain.ingresso.Ingresso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DTO for transferring ticket data to and from the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoDTO {
    private UUID id;
    private UUID eventoId;
    private BigDecimal preco;
    private boolean vendido;
    private LocalDateTime dataVenda;
    private List<IngressoHistoricoDTO> historico;

    public static IngressoDTO fromDomain(Ingresso ingresso) {
        List<IngressoHistoricoDTO> historicoDTO = ingresso.getHistorico().stream()
                .map(h -> new IngressoHistoricoDTO(h.getDescricao(), h.getData()))
                .collect(Collectors.toList());
                
        return new IngressoDTO(
                ingresso.getId().getId(),
                ingresso.getEventoId().getId(),
                ingresso.getPreco().getValor(),
                ingresso.isVendido(),
                ingresso.getDataVenda(),
                historicoDTO
        );
    }
}