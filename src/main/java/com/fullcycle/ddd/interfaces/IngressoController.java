package com.fullcycle.ddd.interfaces;

import com.fullcycle.ddd.application.IngressoService;
import com.fullcycle.ddd.domain.ingresso.Ingresso;
import com.fullcycle.ddd.interfaces.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST Controller for ticket operations.
 * Uses DTOs to isolate the domain model from the API.
 */
@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoDTO> buscarPorId(@PathVariable UUID id) {
        Optional<Ingresso> ingresso = ingressoService.buscarPorId(id);
        return ingresso
                .map(IngressoDTO::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/evento/{eventoId}")
    public List<IngressoDTO> buscarPorEvento(@PathVariable UUID eventoId) {
        return ingressoService.buscarPorEvento(eventoId)
                .stream()
                .map(IngressoDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/disponiveis")
    public List<IngressoDTO> buscarDisponiveis() {
        return ingressoService.buscarIngressosDisponiveis()
                .stream()
                .map(IngressoDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @PostMapping
    public IngressoDTO criarIngresso(@RequestBody CriarIngressoRequest request) {
        Ingresso ingresso = ingressoService.criarIngresso(
                request.getEventoId(),
                request.getPreco()
        );
        return IngressoDTO.fromDomain(ingresso);
    }

    @PostMapping("/{id}/vender")
    public IngressoDTO venderIngresso(@PathVariable UUID id) {
        Ingresso ingresso = ingressoService.venderIngresso(id);
        return IngressoDTO.fromDomain(ingresso);
    }

    @PostMapping("/{id}/cancelar")
    public IngressoDTO cancelarVenda(@PathVariable UUID id) {
        Ingresso ingresso = ingressoService.cancelarVenda(id);
        return IngressoDTO.fromDomain(ingresso);
    }

    @PostMapping("/{id}/desconto")
    public IngressoDTO aplicarDesconto(
            @PathVariable UUID id,
            @RequestBody AplicarDescontoRequest request) {
        Ingresso ingresso = ingressoService.aplicarDesconto(id, request.getPercentual());
        return IngressoDTO.fromDomain(ingresso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirIngresso(@PathVariable UUID id) {
        ingressoService.excluirIngresso(id);
        return ResponseEntity.noContent().build();
    }
}