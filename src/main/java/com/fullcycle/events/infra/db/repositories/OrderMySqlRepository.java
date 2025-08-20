package com.fullcycle.events.infra.db.repositories;

import com.fullcycle.events.domain.entities.Order;

import com.fullcycle.events.domain.repositories.IOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderMySqlRepository implements IOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public OrderMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Order entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<Order> findById(Object id) {
        Order.OrderId orderId;
        if (id instanceof String) {
            orderId = new Order.OrderId((String) id);
        } else if (id instanceof Order.OrderId) {
            orderId = (Order.OrderId) id;
        } else {
            throw new IllegalArgumentException("Invalid ID type");
        }
        Order order = entityManager.find(Order.class, orderId);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery(new String("SELECT o FROM Order o"), Order.class)
                .getResultList();
    }

    @Override
    public void delete(Order entity) {
        entityManager.remove(entity);
    }
}