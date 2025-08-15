package com.fullcycle.ddd.application;

import com.fullcycle.ddd.domain.ingresso.Ingresso;
import com.fullcycle.ddd.domain.ingresso.IngressoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;

    public IngressoService(IngressoRepository ingressoRepository) {
        this.ingressoRepository = ingressoRepository;
    }

    public Optional<Ingresso> buscarPorId(UUID id) {
        return ingressoRepository.findById(id);
    }

    public Ingresso criarIngresso(Ingresso ingresso) {
        return ingressoRepository.save(ingresso);
    }
}