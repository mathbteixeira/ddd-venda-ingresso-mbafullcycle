package com.fullcycle.events.infra.db.repositories;

import com.fullcycle.events.domain.entities.Event;

import com.fullcycle.events.domain.repositories.IEventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventMySqlRepository implements IEventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EventMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Event entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<Event> findById(Object id) {
        Event.EventId eventId;
        if (id instanceof String) {
            eventId = new Event.EventId((String) id);
        } else if (id instanceof Event.EventId) {
            eventId = (Event.EventId) id;
        } else {
            throw new IllegalArgumentException("Invalid ID type");
        }
        Event event = entityManager.find(Event.class, eventId);
        return Optional.ofNullable(event);
    }

    @Override
    public List<Event> findAll() {
        return entityManager.createQuery(new String("SELECT e FROM Event e"), Event.class)
                .getResultList();
    }

    @Override
    public void delete(Event entity) {
        entityManager.remove(entity);
    }
}