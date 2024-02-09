package ru.petproject.inventory.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.MovementNewDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.ItemMapper;
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
import java.util.stream.Collectors;

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
        checkFromAndToParams(movementNewDto);
        checkItemsId(movementNewDto.getItemsId());
        Set<Item> items = itemRepository.findAllByOrganizationAndIdIn(user.getOrganization(), movementNewDto.getItemsId());
        checkExistsItems(movementNewDto.getItemsId(), items);
        Movement movement = Movement.builder()
                .movementDate(LocalDateTime.now())
                .description(movementNewDto.getDescription())
                .items(items)
                .build();
        //setFromAndToOwner(movementNewDto, movement);
        if (movementNewDto.getFromOwnerId() != null && movementNewDto.getToOwnerId() != null) {
            User fromOwner = getUser(user.getOrganization(), movementNewDto.getFromOwnerId());
            checkReporting(fromOwner);
            checkExistsItems(fromOwner, items);
            User toOwner = getUser(user.getOrganization(), movementNewDto.getToOwnerId());
            checkReporting(toOwner);
            movement.setFromOwner(fromOwner);
            movement.setToOwner(toOwner);
            //в оборудовании переписать владельца и сохранить
            items.forEach(i -> i.setOwner(toOwner));
            //itemRepository.saveAll(items);
        }
        //setFromAndToUser(movementNewDto, movement);
        if (movementNewDto.getFromUserId() != null && movementNewDto.getToUserId() != null) {
            User fromUser = getUser(user.getOrganization(), movementNewDto.getFromUserId());
            checkExistsItems(fromUser, items);
            User toUser = getUser(user.getOrganization(), movementNewDto.getToUserId());
            movement.setFromUser(fromUser);
            movement.setToUser(toUser);
            //в оборудовании переписать владельца и сохранить
            items.forEach(i -> i.setUser(toUser));
            //itemRepository.saveAll(items);
        }
        //setFromAndToDepartment(movementNewDto, movement);
        if (movementNewDto.getFromDepartmentId() != null && movementNewDto.getToDepartmentId() != null) {
            Department fromDepartment = getDepartment(user.getOrganization(), movementNewDto.getFromDepartmentId());
            checkExistsItems(fromDepartment, items);
            Department toDepartment = getDepartment(user.getOrganization(), movementNewDto.getToDepartmentId());
            movement.setFromDepartment(fromDepartment);
            movement.setToDepartment(toDepartment);
            //в оборудовании переписать владельца и сохранить
            items.forEach(i -> i.setDepartment(toDepartment));
            //itemRepository.saveAll(items);
        }
        itemRepository.saveAll(items);
        movement = movementRepository.save(movement);
        return MovementMapper.toDto(movement);
    }

    private void checkExistsItems(Department department, Set<Item> items) {
        if (items.stream().noneMatch(i -> Objects.equals(i.getDepartment().getId(), department.getId()))) {
            throw new IllegalArgumentException("Оборудование не находится в отделе");
        }
    }

    private User getUser(Organization organization, Long userId) {
        return userRepository.findByOrganizationAndId(organization, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

//    private void setFromAndToOwner(MovementNewDto movementNewDto, Movement movement) {
//        if (movementNewDto.getFromOwnerId() == null || movementNewDto.getToOwnerId() == null) {
//            return;
//        }
//        User fromOwner = getUser(organization, )
//        if (movement.getItems().stream().anyMatch(item -> item.ge))
//    }

    private void checkItemsId(Set<Long> itemsId) {
        if (itemsId.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Id перемещаемого оборудования должно быть указано");
        }
        if (itemsId.stream().anyMatch(id -> id <= 0)) {
            throw new IllegalArgumentException("Id перемещаемого оборудования должно быть положительным");
        }
    }

    private void checkExistsItems(Set<Long> itemsId, Set<Item> items) {
        for (Long id : itemsId) {
            if (items.stream()
                    .map(Item::getId)
                    .noneMatch(id::equals)) {
                throw new NotFoundException(String.format("Оборудование с id %s не найдено", id));
            }
        }
    }

    private void checkExistsItems(User user, Set<Item> items) {
        if (items.stream().noneMatch(i -> Objects.equals(i.getOwner().getId(), user.getId()))) {
            throw new IllegalArgumentException("Оборудование не находится у пользователя");
        }
    }

    private void checkFromAndToParams(MovementNewDto movementNewDto) {
        if (movementNewDto.getFromOwnerId() == null && movementNewDto.getToOwnerId() == null
                && movementNewDto.getFromUserId() == null && movementNewDto.getToUserId() == null
                && movementNewDto.getFromDepartmentId() == null && movementNewDto.getToDepartmentId() == null) {
            throw new IllegalArgumentException("Нужно указать откуда и куда переносить элемент");
        }
        if (movementNewDto.getFromOwnerId() == null ^ movementNewDto.getToOwnerId() == null) {
            throw new IllegalArgumentException("Нужно указать owner откуда и куда переносить элемент");
        }
        if (movementNewDto.getFromUserId() == null ^ movementNewDto.getToUserId() == null) {
            throw new IllegalArgumentException("Нужно указать user откуда и куда переносить элемент");
        }
        if (movementNewDto.getFromDepartmentId() == null ^ movementNewDto.getToDepartmentId() == null) {
            throw new IllegalArgumentException("Нужно указать department откуда и куда переносить элемент");
        }
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

    public MovementDto getMovement(Long userId, Long id) {
        User user = getUser(userId);
        Movement movement = getMovement(user.getOrganization(), id);
        return MovementMapper.toDto(movement);
    }

    private Movement getMovement(Organization organization, Long id) {
        return movementRepository.findByFromOwner_OrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format("Перемещение с id %d не найдено", id)));
    }

    public List<MovementDto> getMovements(Long userId,
                                          Long fromOwnerId, Long toOwnerId,
                                          Long fromUserId, Long toUserId,
                                          Long fromDepartmentId, Long toDepartmentId,
                                          LocalDateTime fromDate, LocalDateTime toDate,
                                          int from, int size) {
        User user = getUser(userId);
        QMovement qMovement = QMovement.movement;
        Predicate predicate = qMovement.fromOwner.organization.eq(user.getOrganization());
        if (fromOwnerId != null) {
            User fromOwner = getUser(user.getOrganization(), fromOwnerId);
            predicate = qMovement.fromOwner.eq(fromOwner).and(predicate);
        }
        if (toOwnerId != null) {
            User toOwner = getUser(user.getOrganization(), toOwnerId);
            predicate = qMovement.toOwner.eq(toOwner).and(predicate);
        }
        if (fromUserId != null) {
            User fromUser = getUser(user.getOrganization(), fromUserId);
            predicate = qMovement.fromUser.eq(fromUser).and(predicate);
        }
        if (toUserId != null) {
            User toUser = getUser(user.getOrganization(), toUserId);
            predicate = qMovement.toUser.eq(toUser).and(predicate);
        }
        if (fromDepartmentId != null) {
            Department fromDepartment = getDepartment(user.getOrganization(), fromDepartmentId);
            predicate = qMovement.fromDepartment.eq(fromDepartment).and(predicate);
        }
        if (toDepartmentId != null) {
            Department toDepartment = getDepartment(user.getOrganization(), toDepartmentId);
            predicate = qMovement.toDepartment.eq(toDepartment).and(predicate);
        }
        if (fromDate != null) {
            predicate = qMovement.movementDate.after(fromDate).and(predicate);
        }
        if (toDate != null) {
            predicate = qMovement.movementDate.before(toDate).and(predicate);
        }
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Movement> movements = movementRepository.findAll(predicate, pageable).getContent();
        return movements.stream()
                .map(MovementMapper::toDto)
                .collect(Collectors.toList());
    }
}
