package ru.petproject.inventory.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.repository.UserRepository;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BaseUserService {
    private static final String USER_NOT_FOUND = "Пользователь с id %d не найден";
    private static final String USER_NOT_ADMIN = "Пользователь с id %d не является администратором";
    private static final String EMAIL_ALREADY_EXISTS = "Пользователь с email %s уже существует";
    private static final String NAME_ALREADY_EXISTS = "Пользователь %s уже существует";
    private static final String SINGLE_ADMIN = "Пользователь %s единственный администратор";
    private static final String USER_NOT_REPORTING = "Пользователь с id %d не является подотчетным лицом";
    private final UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    public User getUser(Organization organization, Long id) {
        return userRepository.findByOrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    public List<User> getUsers(Organization organization) {
        return userRepository.findAllByOrganization(organization);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void checkUserByAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format(USER_NOT_ADMIN, user.getId()));
        }
    }

    public void checkUserByReporting(User user) {
        if (!user.isReporting()) {
            throw new IllegalArgumentException(String.format(USER_NOT_REPORTING, user.getId()));
        }
    }

    public void checkExistsUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS, email));
        }
    }

    public void checkExistsUser(String email, Long exceptThisId) {
        if (userRepository.existsByEmailAndIdNot(email, exceptThisId)) {
            throw new AlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS, email));
        }
    }

    public void checkExistsUser(Organization organization, String name) {
        if (userRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }

    public void checkExistsUser(Organization organization, String name, Long exceptThisId) {
        if (userRepository.existsByOrganizationAndNameAndIdNot(organization, name, exceptThisId)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }

    public void checkExistsAdmin(Organization organization, User exceptThisUser) {
        if (!userRepository.existsByOrganizationAndRoleAndIdNot(organization, Role.ADMIN, exceptThisUser.getId())) {
            throw new AccessDeniedException(String.format(SINGLE_ADMIN, exceptThisUser.getName()));
        }
    }

    public String generatePassword() {
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
}
