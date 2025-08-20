package com.fullcycle.events.infra.db.repositories;

import com.fullcycle.events.domain.entities.Order;
import com.fullcycle.events.domain.entities.Partner;
import com.fullcycle.events.domain.repositories.IPartnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PartnerMySqlRepository implements IPartnerRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PartnerMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Partner entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<Partner> findById(Object id) {
        Partner.PartnerId partnerId;
        if (id instanceof String) {
            partnerId = new Partner.PartnerId((String) id);
        } else if (id instanceof Order.OrderId) {
            partnerId = (Partner.PartnerId) id;
        } else {
            throw new IllegalArgumentException("Invalid ID type");
        }
        Partner partner = entityManager.find(Partner.class, partnerId);
        return Optional.ofNullable(partner);
    }

    @Override
    public List<Partner> findAll() {
        TypedQuery<Partner> query = entityManager.createQuery(new String("SELECT p FROM Partner p"), Partner.class);
        return query.getResultList();
    }

    @Override
    public void delete(Partner entity) {
        entityManager.remove(entity);
    }
}