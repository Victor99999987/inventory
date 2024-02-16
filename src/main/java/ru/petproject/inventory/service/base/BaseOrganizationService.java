package ru.petproject.inventory.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.repository.OrganizationRepository;

@Service
@RequiredArgsConstructor
public class BaseOrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }
}
