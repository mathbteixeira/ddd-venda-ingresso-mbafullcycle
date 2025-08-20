package com.fullcycle.storedevents.infra.db.repositories;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent.StoredEventId;
import com.fullcycle.storedevents.domain.repositories.IStoredEventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoredEventMySqlRepository implements IStoredEventRepository {

    private final EntityManager entityManager;

    public StoredEventMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<StoredEvent> allBetween(StoredEventId lowEventId, StoredEventId highEventId) {
        String jpql = new String("SELECT e FROM StoredEventSchema e WHERE e.id >= :lowId AND e.id <= :highId ORDER BY e.id ASC");
        TypedQuery<StoredEvent> query = entityManager.createQuery(jpql, StoredEvent.class);
        query.setParameter("lowId", lowEventId);
        query.setParameter("highId", highEventId);
        return query.getResultList();
    }

    @Override
    public List<StoredEvent> allSince(StoredEventId eventId) {
        String jpql = new String("SELECT e FROM StoredEventSchema e WHERE e.id >= :eventId ORDER BY e.id ASC");
        TypedQuery<StoredEvent> query = entityManager.createQuery(jpql, StoredEvent.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    @Override
    public StoredEvent add(IDomainEvent domainEvent) {
        StoredEvent storedEvent = StoredEvent.create(domainEvent);
        entityManager.persist(storedEvent);
        return storedEvent;
    }
}