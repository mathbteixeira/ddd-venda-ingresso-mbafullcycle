package com.fullcycle.events.infra.db;

import com.fullcycle.events.domain.entities.Partner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartnerSchemaTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        // "eventsPU" deve estar configurado no persistence.xml
        emf = Persistence.createEntityManagerFactory("eventsPU");
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    public void deveCriarUmPartner() {
        em.getTransaction().begin();

        Partner partner = Partner.create("Partner 1");
        em.persist(partner);
        em.getTransaction().commit();

        // Limpar cache do EntityManager
        em.clear();

        Partner partnerFound = em.find(Partner.class, partner.getId());
        assertNotNull(partnerFound);
        assertEquals(partner.getId(), partnerFound.getId());
        assertEquals("Partner 1", partnerFound.getName());

        System.out.println("Partner ID: " + partner.getId());
        System.out.println("Partner Name: " + partnerFound.getName());
    }
}