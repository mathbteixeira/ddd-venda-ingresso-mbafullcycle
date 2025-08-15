package com.fullcycle.ddd.domain.ingresso.events;

import com.fullcycle.ddd.domain.events.AbstractDomainEvent;
import com.fullcycle.ddd.domain.ingresso.Ingresso;
import com.fullcycle.ddd.domain.ingresso.IngressoId;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event that is published when a ticket is sold.
 */
@Getter
public class IngressoVendidoEvent extends AbstractDomainEvent {
    private final UUID ingressoId;
    private final UUID eventoId;
    private final BigDecimal preco;
    private final LocalDateTime dataVenda;

    public IngressoVendidoEvent(Ingresso ingresso) {
        super();
        this.ingressoId = ingresso.getId().getId();
        this.eventoId = ingresso.getEventoId().getId();
        this.preco = ingresso.getPreco().getValor();
        this.dataVenda = ingresso.getDataVenda();
    }
}