package com.fullcycle.config;

import com.fullcycle.domain.DomainEventManager;
import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent;
import com.fullcycle.storedevents.domain.repositories.IStoredEventRepository;
import com.fullcycle.storedevents.infra.db.repositories.StoredEventMySqlRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventsConfig implements InitializingBean {

    private final DomainEventManager domainEventManager;
    private final IStoredEventRepository storedEventRepository;

    public DomainEventsConfig(DomainEventManager domainEventManager,
                              IStoredEventRepository storedEventRepository) {
        this.domainEventManager = domainEventManager;
        this.storedEventRepository = storedEventRepository;
    }

    @Bean
    public DomainEventManager domainEventManager() {
        return new DomainEventManager();
    }

    @Bean
    public IStoredEventRepository storedEventRepository(EntityManager em) {
        return new StoredEventMySqlRepository(em);
    }

    @Override
    public void afterPropertiesSet() {
        domainEventManager.register("*", (IDomainEvent event) -> {
            storedEventRepository.add((IDomainEvent) StoredEvent.create(event));
        });
    }
}