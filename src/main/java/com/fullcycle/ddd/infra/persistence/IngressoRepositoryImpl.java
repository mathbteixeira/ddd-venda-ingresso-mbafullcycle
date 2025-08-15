package com.fullcycle.ddd.infra.persistence;

import com.fullcycle.ddd.domain.evento.EventoId;
import com.fullcycle.ddd.domain.ingresso.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class IngressoRepositoryImpl implements IngressoRepository {

    private final IngressoJpaRepository jpaRepository;

    public IngressoRepositoryImpl(IngressoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Ingresso> findById(IngressoId id) {
        IngressoJpaEntity entity = jpaRepository.findByIdWithHistorico(id.getId());
        return Optional.ofNullable(entity).map(this::mapToIngresso);
    }

    @Override
    public List<Ingresso> findByEventoId(EventoId eventoId) {
        return jpaRepository.findByEventoId(eventoId.getId())
                .stream()
                .map(this::mapToIngresso)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> findByVendido(boolean vendido) {
        return jpaRepository.findByVendido(vendido)
                .stream()
                .map(this::mapToIngresso)
                .collect(Collectors.toList());
    }

    @Override
    public Ingresso save(Ingresso ingresso) {
        IngressoJpaEntity entity = mapToJpaEntity(ingresso);
        IngressoJpaEntity saved = jpaRepository.save(entity);
        return mapToIngresso(saved);
    }

    @Override
    public void delete(IngressoId id) {
        jpaRepository.deleteById(id.getId());
    }

    private Ingresso mapToIngresso(IngressoJpaEntity entity) {
        // This is a simplified mapping that doesn't fully reconstruct the aggregate
        // In a real implementation, you would need to use reflection or a factory method
        // that allows setting private fields
        
        // Create a new Ingresso with the basic fields
        Ingresso ingresso = Ingresso.criar(
                EventoId.of(entity.getEventoId()),
                PrecoIngresso.of(entity.getPreco())
        );
        
        // Use reflection to set the ID and other fields
        try {
            java.lang.reflect.Field idField = Ingresso.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(ingresso, IngressoId.of(entity.getId()));
            
            java.lang.reflect.Field vendidoField = Ingresso.class.getDeclaredField("vendido");
            vendidoField.setAccessible(true);
            vendidoField.set(ingresso, entity.isVendido());
            
            if (entity.getDataVenda() != null) {
                java.lang.reflect.Field dataVendaField = Ingresso.class.getDeclaredField("dataVenda");
                dataVendaField.setAccessible(true);
                dataVendaField.set(ingresso, entity.getDataVenda());
            }
            
            // Map the history entries
            java.lang.reflect.Field historicoField = Ingresso.class.getDeclaredField("historico");
            historicoField.setAccessible(true);
            List<IngressoHistorico> historico = entity.getHistorico().stream()
                    .map(h -> new IngressoHistorico(h.getDescricao(), h.getData()))
                    .collect(Collectors.toList());
            historicoField.set(ingresso, historico);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear entidade JPA para domínio", e);
        }
        
        return ingresso;
    }

    private IngressoJpaEntity mapToJpaEntity(Ingresso ingresso) {
        IngressoJpaEntity entity = new IngressoJpaEntity();
        entity.setId(ingresso.getId().getId());
        entity.setEventoId(ingresso.getEventoId().getId());
        entity.setPreco(ingresso.getPreco().getValor());
        entity.setVendido(ingresso.isVendido());
        entity.setDataVenda(ingresso.getDataVenda());
        
        List<IngressoHistoricoJpaEntity> historicoEntities = new ArrayList<>();
        for (IngressoHistorico historico : ingresso.getHistorico()) {
            historicoEntities.add(new IngressoHistoricoJpaEntity(
                    entity.getId(),
                    historico.getDescricao(),
                    historico.getData()
            ));
        }
        entity.setHistorico(historicoEntities);
        
        return entity;
    }
}