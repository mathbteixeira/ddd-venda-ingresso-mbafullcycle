package com.fullcycle.ddd.application;

import com.fullcycle.ddd.domain.evento.EventoId;
import com.fullcycle.ddd.domain.ingresso.Ingresso;
import com.fullcycle.ddd.domain.ingresso.IngressoId;
import com.fullcycle.ddd.domain.ingresso.IngressoRepository;
import com.fullcycle.ddd.domain.ingresso.PrecoIngresso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service for ticket operations.
 * This layer orchestrates domain operations and transaction boundaries.
 */
@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;

    public IngressoService(IngressoRepository ingressoRepository) {
        this.ingressoRepository = ingressoRepository;
    }

    public Optional<Ingresso> buscarPorId(UUID id) {
        return ingressoRepository.findById(IngressoId.of(id));
    }

    public List<Ingresso> buscarPorEvento(UUID eventoId) {
        return ingressoRepository.findByEventoId(EventoId.of(eventoId));
    }

    public List<Ingresso> buscarIngressosDisponiveis() {
        return ingressoRepository.findByVendido(false);
    }

    @Transactional
    public Ingresso criarIngresso(UUID eventoId, BigDecimal preco) {
        Ingresso ingresso = Ingresso.criar(
                EventoId.of(eventoId),
                PrecoIngresso.of(preco)
        );
        return ingressoRepository.save(ingresso);
    }

    @Transactional
    public Ingresso venderIngresso(UUID id) {
        Ingresso ingresso = ingressoRepository.findById(IngressoId.of(id))
                .orElseThrow(() -> new IllegalArgumentException("Ingresso não encontrado"));
        
        ingresso.vender();
        return ingressoRepository.save(ingresso);
    }

    @Transactional
    public Ingresso cancelarVenda(UUID id) {
        Ingresso ingresso = ingressoRepository.findById(IngressoId.of(id))
                .orElseThrow(() -> new IllegalArgumentException("Ingresso não encontrado"));
        
        ingresso.cancelarVenda();
        return ingressoRepository.save(ingresso);
    }

    @Transactional
    public Ingresso aplicarDesconto(UUID id, BigDecimal percentual) {
        Ingresso ingresso = ingressoRepository.findById(IngressoId.of(id))
                .orElseThrow(() -> new IllegalArgumentException("Ingresso não encontrado"));
        
        ingresso.aplicarDesconto(percentual);
        return ingressoRepository.save(ingresso);
    }

    @Transactional
    public void excluirIngresso(UUID id) {
        ingressoRepository.delete(IngressoId.of(id));
    }
}