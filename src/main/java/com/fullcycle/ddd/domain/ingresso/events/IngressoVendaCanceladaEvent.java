package com.fullcycle.ddd.domain.ingresso.events;

import com.fullcycle.ddd.domain.events.AbstractDomainEvent;
import com.fullcycle.ddd.domain.ingresso.Ingresso;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain event that is published when a ticket sale is canceled.
 */
@Getter
public class IngressoVendaCanceladaEvent extends AbstractDomainEvent {
    private final UUID ingressoId;
    private final UUID eventoId;
    private final BigDecimal preco;

    public IngressoVendaCanceladaEvent(Ingresso ingresso) {
        super();
        this.ingressoId = ingresso.getId().getId();
        this.eventoId = ingresso.getEventoId().getId();
        this.preco = ingresso.getPreco().getValor();
    }
}