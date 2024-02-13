package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.dto.OrganizationUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.OrganizationMapper;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.repository.OrganizationRepository;
import ru.petproject.inventory.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BaseOrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }
}
