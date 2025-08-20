package com.fullcycle.storedevents.domain.repositories;

import com.fullcycle.domain.IDomainEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent;
import com.fullcycle.storedevents.domain.entities.StoredEvent.StoredEventId;

import java.util.List;

public interface IStoredEventRepository {

    List<StoredEvent> allBetween(StoredEventId lowEventId, StoredEventId highEventId);

    List<StoredEvent> allSince(StoredEventId eventId);

    StoredEvent add(IDomainEvent domainEvent);
}