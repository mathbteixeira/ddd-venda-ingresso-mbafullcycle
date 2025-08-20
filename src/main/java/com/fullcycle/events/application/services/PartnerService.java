package com.fullcycle.events.application.services;


import com.fullcycle.application.ApplicationService;
import com.fullcycle.events.domain.entities.Partner;
import com.fullcycle.events.domain.repositories.IPartnerRepository;

import java.util.List;

public class PartnerService {

    private final IPartnerRepository partnerRepo;
    private final ApplicationService applicationService;

    public PartnerService(IPartnerRepository partnerRepo, ApplicationService applicationService) {
        this.partnerRepo = partnerRepo;
        this.applicationService = applicationService;
    }

    public List<Partner> list() throws Exception {
        return partnerRepo.findAll();
    }

    public Partner create(String name) throws Exception {
        return applicationService.run(() -> {
            Partner partner = Partner.create(name);
            partnerRepo.add(partner);
            return partner;
        });
    }

    public Partner update(String id, String name) throws Exception {
        return applicationService.run(() -> {
            Partner partner = partnerRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Partner not found"));

            if (name != null && !name.isEmpty()) {
                partner.changeName(name);
            }

            partnerRepo.add(partner);
            return partner;
        });
    }
}