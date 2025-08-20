package com.fullcycle.events.infra.db.repositories;

import com.fullcycle.events.domain.entities.Order;
import com.fullcycle.events.domain.entities.SpotReservation;
import com.fullcycle.events.domain.repositories.ISpotReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpotReservationMySqlRepository implements ISpotReservationRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public SpotReservationMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(SpotReservation entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<SpotReservation> findById(Object id) {
        SpotReservation.SpotReservationId spotReservationId;
        if (id instanceof String) {
            spotReservationId = new SpotReservation.SpotReservationId((String) id);
        } else if (id instanceof Order.OrderId) {
            spotReservationId = (SpotReservation.SpotReservationId) id;
        } else {
            throw new IllegalArgumentException("Invalid ID type");
        }
        SpotReservation partner = entityManager.find(SpotReservation.class, spotReservationId);
        return Optional.ofNullable(partner);
    }

    @Override
    public List<SpotReservation> findAll() {
        TypedQuery<SpotReservation> query = entityManager.createQuery(new String("SELECT p FROM SpotReservation p"), SpotReservation.class);
        return query.getResultList();
    }

    @Override
    public void delete(SpotReservation entity) {
        entityManager.remove(entity);
    }
}