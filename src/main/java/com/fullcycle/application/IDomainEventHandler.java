package com.fullcycle.application;

import com.fullcycle.domain.IDomainEvent;

public interface IDomainEventHandler<E extends IDomainEvent> {
    void handle(E event) throws Exception;
}
