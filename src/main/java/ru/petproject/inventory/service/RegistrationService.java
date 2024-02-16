package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.RegistrationDto;
import ru.petproject.inventory.dto.RegistrationNewDto;
import ru.petproject.inventory.mapper.RegistrationMapper;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.service.base.BaseOrganizationService;
import ru.petproject.inventory.service.base.BaseUserService;

import static ru.petproject.inventory.common.Const.SERVER_HOST;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
    private final BaseOrganizationService baseOrganizationService;
    private final BaseUserService baseUserService;
    private final EmailService emailService;

    @Transactional
    public RegistrationDto postRegistration(RegistrationNewDto registrationNewDto) {
        baseUserService.checkExistsUser(registrationNewDto.getEmail());
        Organization organization = Organization.builder()
                .name(registrationNewDto.getOrganizationName())
                .activatedCode(baseUserService.generatePassword())
                .activated(false)
                .build();
        organization = baseOrganizationService.saveOrganization(organization);
        User user = User.builder()
                .name(registrationNewDto.getUserName())
                .reporting(true)
                .position(registrationNewDto.getPosition())
                .email(registrationNewDto.getEmail())
                .password(baseUserService.generatePassword())
                .role(Role.ADMIN)
                .organization(organization)
                .build();
        user = baseUserService.saveUser(user);
        emailService.sendSimpleEmail(registrationNewDto.getEmail(),
                "Подтверждение регистрации",
                "Для подтверждения регистрации перейдите по ссылке " +
                        SERVER_HOST + "/activate/" + user.getId() + "?code=" + organization.getActivatedCode());
        return RegistrationMapper.toDto(user);
    }

    @Transactional
    public RegistrationDto patchActivate(Long userId, String activateCode) {
        User user = baseUserService.getUser(userId);
        Organization organization = user.getOrganization();
        if (!organization.getActivatedCode().equals(activateCode)) {
            throw new IllegalArgumentException("Неверный код активации");
        }
        organization.setActivated(true);
        organization = baseOrganizationService.saveOrganization(organization);
        emailService.sendSimpleEmail(user.getEmail(),
                "Данные для входа в систему ",
                "организация " + organization.getName() +
                        "логин " + user.getEmail() +
                        "пароль " + user.getPassword());
        return RegistrationMapper.toDto(user);
    }
}
