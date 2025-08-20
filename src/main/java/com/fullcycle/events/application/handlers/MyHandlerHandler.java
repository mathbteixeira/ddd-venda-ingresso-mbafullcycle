package com.fullcycle.events.application.handlers;

import com.fullcycle.application.IDomainEventHandler;
import com.fullcycle.domain.DomainEventManager;
import com.fullcycle.events.domain.events.PartnerCreated;
import com.fullcycle.events.domain.repositories.IPartnerRepository;
import org.springframework.stereotype.Component;

@Component
public class MyHandlerHandler implements IDomainEventHandler<PartnerCreated> {

    private final IPartnerRepository partnerRepo;
    private final DomainEventManager domainEventManager;

    public MyHandlerHandler(IPartnerRepository partnerRepo, DomainEventManager domainEventManager) {
        this.partnerRepo = partnerRepo;
        this.domainEventManager = domainEventManager;
    }

    @Override
    public void handle(PartnerCreated event) {
        System.out.println(event);
        // manipular agregados
        // partnerRepo.add(agregado);
        // domainEventManager.publish(agregado);
    }

    /**
     * Eventos que este handler escuta.
     */
    public static String[] listensTo() {
        return new String[]{PartnerCreated.class.getSimpleName()};
    }
}