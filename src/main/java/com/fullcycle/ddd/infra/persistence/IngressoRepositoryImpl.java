package com.fullcycle.ddd.infra.persistence;

import com.fullcycle.ddd.domain.ingresso.Ingresso;
import com.fullcycle.ddd.domain.ingresso.IngressoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class IngressoRepositoryImpl implements IngressoRepository {

    private final IngressoJpaRepository jpaRepository;

    public IngressoRepositoryImpl(IngressoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Ingresso> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> new Ingresso(
                        entity.getId(),
                        entity.getEvento(),
                        entity.getPreco(),
                        entity.isVendido()
                ));
    }

    @Override
    public Ingresso save(Ingresso ingresso) {
        IngressoJpaEntity entity = new IngressoJpaEntity(
                ingresso.getId(),
                ingresso.getEvento(),
                ingresso.getPreco(),
                ingresso.isVendido()
        );
        IngressoJpaEntity saved = jpaRepository.save(entity);
        return new Ingresso(
                saved.getId(),
                saved.getEvento(),
                saved.getPreco(),
                saved.isVendido()
        );
    }
}