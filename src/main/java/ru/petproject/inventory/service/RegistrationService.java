package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.RegistrationDto;
import ru.petproject.inventory.dto.RegistrationNewDto;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.RegistrationMapper;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.repository.OrganizationRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.util.Random;

import static ru.petproject.inventory.common.Const.SERVER_HOST;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public RegistrationDto postRegistration(RegistrationNewDto registrationNewDto) {
        checkExistsUser(registrationNewDto.getEmail());
        Organization organization = Organization.builder()
                .name(registrationNewDto.getOrganizationName())
                .activatedCode(generatePassword())
                .activated(false)
                .build();
        organization = organizationRepository.save(organization);
        User user = User.builder()
                .name(registrationNewDto.getUserName())
                .reporting(true)
                .position(registrationNewDto.getPosition())
                .email(registrationNewDto.getEmail())
                .password(generatePassword())
                .role(Role.ADMIN)
                .organization(organization)
                .build();
        user = userRepository.save(user);
        emailService.sendSimpleEmail(registrationNewDto.getEmail(),
                "Подтверждение регистриции",
                "Для подтверждения регистрации перейдите по ссылке " +
                        SERVER_HOST + "/activate/" + user.getId() + "?code=" + organization.getActivatedCode());
        return RegistrationMapper.toDto(user);
    }

    @Transactional
    public RegistrationDto patchActivate(Long userId, String activateCode) {
        User user = getUser(userId);
        Organization organization = user.getOrganization();
        if (!organization.getActivatedCode().equals(activateCode)) {
            throw new IllegalArgumentException("Неверный код активации");
        }
        organization.setActivated(true);
        organization = organizationRepository.save(organization);
        emailService.sendSimpleEmail(user.getEmail(),
                "Данные для входа в систему",
                "организация " + organization.getName() +
                        "логин " + user.getEmail() +
                        "пароль " + user.getPassword());
        return RegistrationMapper.toDto(user);
    }

    private void checkExistsUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(String.format("Пользователь с email %s уже существует", email));
        }
    }

    private String generatePassword() {
        String alphabet = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
        int length = 10;
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        int index;
        for (int i = 0; i < length; i++) {
            index = random.nextInt(alphabet.length());
            password.append(alphabet.charAt(index));
        }
        return password.toString();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }
}
