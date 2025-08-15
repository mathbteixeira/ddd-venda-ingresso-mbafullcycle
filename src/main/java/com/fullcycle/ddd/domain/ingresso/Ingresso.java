package com.fullcycle.ddd.domain.ingresso;

import com.fullcycle.ddd.domain.evento.EventoId;
import com.fullcycle.ddd.domain.events.DomainEvent;
import com.fullcycle.ddd.domain.ingresso.events.IngressoVendaCanceladaEvent;
import com.fullcycle.ddd.domain.ingresso.events.IngressoVendidoEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Ingresso is an Aggregate Root in the domain.
 * It encapsulates all business rules related to tickets.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingresso {
    private IngressoId id;
    private EventoId eventoId;
    private PrecoIngresso preco;
    private boolean vendido;
    private LocalDateTime dataVenda;
    private List<IngressoHistorico> historico;

    private Ingresso(IngressoId id, EventoId eventoId, PrecoIngresso preco) {
        this.id = id;
        this.eventoId = eventoId;
        this.preco = preco;
        this.vendido = false;
        this.historico = new ArrayList<>();
        this.historico.add(new IngressoHistorico("Ingresso criado", LocalDateTime.now()));
    }

    public static Ingresso criar(EventoId eventoId, PrecoIngresso preco) {
        if (eventoId == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo");
        }
        if (preco == null) {
            throw new IllegalArgumentException("Preço não pode ser nulo");
        }
        return new Ingresso(IngressoId.generate(), eventoId, preco);
    }

    public void vender() {
        if (this.vendido) {
            throw new IllegalStateException("Ingresso já foi vendido");
        }
        this.vendido = true;
        this.dataVenda = LocalDateTime.now();
        this.historico.add(new IngressoHistorico("Ingresso vendido", this.dataVenda));
    }

    public void cancelarVenda() {
        if (!this.vendido) {
            throw new IllegalStateException("Ingresso não está vendido");
        }
        this.vendido = false;
        this.dataVenda = null;
        this.historico.add(new IngressoHistorico("Venda cancelada", LocalDateTime.now()));
    }

    public void aplicarDesconto(BigDecimal percentual) {
        if (this.vendido) {
            throw new IllegalStateException("Não é possível aplicar desconto em ingresso já vendido");
        }
        PrecoIngresso precoAntigo = this.preco;
        this.preco = this.preco.aplicarDesconto(percentual);
        this.historico.add(new IngressoHistorico(
                "Desconto aplicado: de " + precoAntigo + " para " + this.preco,
                LocalDateTime.now()));
    }

    public List<IngressoHistorico> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}