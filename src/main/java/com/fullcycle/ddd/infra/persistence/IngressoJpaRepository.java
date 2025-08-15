package com.fullcycle.ddd.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngressoJpaRepository extends JpaRepository<IngressoJpaEntity, UUID> {
}