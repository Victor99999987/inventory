package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.dto.OrganizationUpdateDto;
import ru.petproject.inventory.mapper.OrganizationMapper;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.service.base.BaseOrganizationService;
import ru.petproject.inventory.service.base.BaseUserService;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final BaseOrganizationService baseOrganizationService;
    private final BaseUserService baseUserService;

    @Transactional
    public OrganizationDto patchOrganization(Long userId, OrganizationUpdateDto organizationUpdateDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Organization organization = user.getOrganization();
        organization.setName(organizationUpdateDto.getName());
        organization = baseOrganizationService.saveOrganization(organization);
        return OrganizationMapper.toDto(organization);
    }

    @Transactional
    public OrganizationDto getOrganization(Long userId) {
        User user = baseUserService.getUser(userId);
        return OrganizationMapper.toDto(user.getOrganization());
    }
}
