package com.fullcycle.ddd.interfaces;

import com.fullcycle.ddd.application.IngressoService;
import com.fullcycle.ddd.domain.ingresso.Ingresso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingresso> buscarPorId(@PathVariable UUID id) {
        Optional<Ingresso> ingresso = ingressoService.buscarPorId(id);
        return ingresso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ingresso criarIngresso(@RequestBody Ingresso ingresso) {
        return ingressoService.criarIngresso(ingresso);
    }
}