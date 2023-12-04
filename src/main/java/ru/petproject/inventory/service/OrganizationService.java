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
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrganizationDto patchOrganization(Long userId, OrganizationUpdateDto organizationUpdateDto) {
        User user = getUser(userId);
        checkAccess(user);
        Organization organization = user.getOrganization();
        organization.setName(organizationUpdateDto.getName());
        organization = organizationRepository.save(organization);
        return OrganizationMapper.toDto(organization);
    }

    @Transactional
    public OrganizationDto getOrganization(Long userId) {
        User user = getUser(userId);
        return OrganizationMapper.toDto(user.getOrganization());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }
}
