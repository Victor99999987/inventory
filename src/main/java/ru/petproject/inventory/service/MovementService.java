package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.MovementNewDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.MovementMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.repository.DepartmentRepository;
import ru.petproject.inventory.repository.ItemRepository;
import ru.petproject.inventory.repository.MovementRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ItemRepository itemRepository;
    private final MovementRepository movementRepository;

    @Transactional
    public MovementDto postMovement(Long userId, MovementNewDto movementNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        List<User> users = getUsers(user.getOrganization(),
                List.of(movementNewDto.getFromOwnerId(),
                        movementNewDto.getToOwnerId(),
                        movementNewDto.getFromUserId(),
                        movementNewDto.getToUserId()));
        User fromOwner = getUser(users, movementNewDto.getFromOwnerId());
        User toOwner = getUser(users, movementNewDto.getToOwnerId());
        User fromUser = getUser(users, movementNewDto.getFromUserId());
        User toUser = getUser(users, movementNewDto.getToUserId());
        checkReporting(fromOwner);
        checkReporting(toOwner);

        Department fromDepartment = getDepartment(user.getOrganization(), movementNewDto.getFromDepartmentId());
        Department toDepartment = getDepartment(user.getOrganization(), movementNewDto.getToDepartmentId());
        Set<Item> items = itemRepository.findAllByOrganizationAndIdIn(user.getOrganization(), movementNewDto.getItemsId());
        //проверить оборудование у чела есть или нету овнер и юзер
        Movement movement = Movement.builder()
                .movementDate(LocalDateTime.now())
                .fromOwner(fromOwner)
                .toOwner(toOwner)
                .fromUser(fromUser)
                .toUser(toUser)
                .fromDepartment(fromDepartment)
                .toDepartment(toDepartment)
                .items(items)
                .build();
        if (movementNewDto.getDescription() != null) {
            checkBlank("description", movementNewDto.getDescription());
            movement.setDescription(movementNewDto.getDescription());
        }
        movement = movementRepository.save(movement);
        return MovementMapper.toDto(movement);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private List<User> getUsers(Organization organization, List<Long> usersId) {
        return userRepository.findByOrganizationAndIdIn(organization, usersId);
    }

    private User getUser(List<User> users, Long userId) {
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }
        throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }

    private Department getDepartment(Organization organization, Long departmentId) {
        return departmentRepository.findByOrganizationAndId(organization, departmentId)
                .orElseThrow(() -> new NotFoundException(String.format("Подразделение с id %d не найдено", departmentId)));
    }

    private void checkBlank(String name, String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле " + name + " не может состоять из пробелов");
        }
    }

    private void checkReporting(User user) {
        if (!user.isReporting()) {
            throw new IllegalArgumentException(String.format("Пользователь с id %d не является подотчетным", user.getId()));
        }
    }
}
