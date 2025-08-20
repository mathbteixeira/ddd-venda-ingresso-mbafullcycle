package com.fullcycle.events.infra.db.repositories;

import com.fullcycle.events.domain.entities.Customer;

import com.fullcycle.events.domain.repositories.ICustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerMySqlRepository implements ICustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerMySqlRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Customer entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<Customer> findById(Object id) {
        Customer.CustomerId customerId;
        if (id instanceof String) {
            customerId = new Customer.CustomerId((String) id);
        } else if (id instanceof Customer.CustomerId) {
            customerId = (Customer.CustomerId) id;
        } else {
            throw new IllegalArgumentException("Invalid ID type");
        }
        return Optional.ofNullable(entityManager.find(Customer.class, customerId));
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery(new String("SELECT c FROM Customer c"), Customer.class)
                .getResultList();
    }

    @Override
    public void delete(Customer entity) {
        entityManager.remove(entity);
    }
}