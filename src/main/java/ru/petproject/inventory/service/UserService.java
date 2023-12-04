package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.dto.UserNewDto;
import ru.petproject.inventory.dto.UserUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.UserMapper;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.repository.UserRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto postUser(Long userId, UserNewDto userNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        checkExistsUser(userNewDto.getEmail());
        checkExistsUser(user.getOrganization(), userNewDto.getName());
        User newUser = User.builder()
                .name(userNewDto.getName())
                .reporting(userNewDto.getReporting())
                .position(userNewDto.getPosition())
                .email(userNewDto.getEmail())
                .password(generatePassword())
                .role(userNewDto.getRole())
                .organization(user.getOrganization())
                .build();
        newUser = userRepository.save(newUser);
        return UserMapper.toDto(newUser);
    }

    @Transactional
    public UserDto patchUser(Long userId, Long id, UserUpdateDto userUpdateDto) {
        User user = getUser(userId);
        checkAccess(user);
        User patchUser = getUser(user.getOrganization(), id);
        if (userUpdateDto.getName() != null) {
            checkBlank("name", userUpdateDto.getName());
            checkExistsUser(user.getOrganization(), userUpdateDto.getName(), id);
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getReporting() != null) {
            user.setReporting(userUpdateDto.getReporting());
        }
        if (userUpdateDto.getPosition() != null) {
            checkBlank("position", userUpdateDto.getPosition());
            user.setPosition(userUpdateDto.getPosition());
        }
        if (userUpdateDto.getEmail() != null) {
            checkBlank("email", userUpdateDto.getEmail());
            checkExistsUser(userUpdateDto.getEmail(), id);
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getRole() != null) {
            user.setRole(userUpdateDto.getRole());
        }
        patchUser = userRepository.save(patchUser);
        return UserMapper.toDto(patchUser);
    }

    @Transactional
    public void deleteUser(Long userId, Long id) {
        User user = getUser(userId);
        checkAccess(user);
        User deleteUser = getUser(user.getOrganization(), id);
        //нужно глянуть, может есть связанное оборудование
        userRepository.delete(deleteUser);
    }

    @Transactional
    public List<UserDto> getUsers(Long userId) {
        User user = getUser(userId);
        return userRepository.findAllByOrganization(user.getOrganization()).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private User getUser(Organization organization, Long userId) {
        return userRepository.findByOrganizationAndId(organization, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }

    private void checkExistsUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(String.format("Пользователь с email %s уже существует", email));
        }
    }

    private void checkExistsUser(String email, Long notThisId) {
        if (userRepository.existsByEmailAndIdNot(email, notThisId)) {
            throw new AlreadyExistsException(String.format("Пользователь с email %s уже существует", email));
        }
    }

    private void checkExistsUser(Organization organization, String name) {
        if (userRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format("Пользователь с name %s уже существует", name));
        }
    }

    private void checkExistsUser(Organization organization, String name, Long notThisId) {
        if (userRepository.existsByOrganizationAndNameAndIdNot(organization, name, notThisId)) {
            throw new AlreadyExistsException(String.format("Пользователь с name %s уже существует", name));
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

    private void checkBlank(String name, String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле " + name + " не может состоять из пробелов");
        }
    }
}
