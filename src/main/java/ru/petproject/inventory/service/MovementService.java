package ru.petproject.inventory.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.MovementNewDto;
import ru.petproject.inventory.mapper.MovementMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.service.base.BaseDepartmentService;
import ru.petproject.inventory.service.base.BaseItemService;
import ru.petproject.inventory.service.base.BaseMovementService;
import ru.petproject.inventory.service.base.BaseUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementService {
    private static final String FROM_AND_TO_MUST_BE_NOT_NULL = "Нужно указать откуда и куда переносить элементы";
    private final BaseUserService baseUserService;
    private final BaseDepartmentService baseDepartmentService;
    private final BaseItemService baseItemService;
    private final BaseMovementService baseMovementService;

    @Transactional
    public MovementDto postMovement(Long userId, MovementNewDto movementNewDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        checkFromAndToParams(movementNewDto);
        Utility.checkNotNullIds(movementNewDto.getItemsId());
        Utility.checkPositiveIds(movementNewDto.getItemsId());
        Set<Item> items = baseItemService.getItems(user.getOrganization(), movementNewDto.getItemsId());
        baseItemService.checkItemsByIds(items, movementNewDto.getItemsId());
        Movement movement = Movement.builder()
                .movementDate(LocalDateTime.now())
                .description(movementNewDto.getDescription())
                .build();
        if (movementNewDto.getFromOwnerId() != null && movementNewDto.getToOwnerId() != null) {
            User fromOwner = baseUserService.getUser(user.getOrganization(), movementNewDto.getFromOwnerId());
            baseUserService.checkUserByReporting(fromOwner);
            baseItemService.checkItemsByOwner(items, fromOwner);
            User toOwner = baseUserService.getUser(user.getOrganization(), movementNewDto.getToOwnerId());
            baseUserService.checkUserByReporting(toOwner);
            movement.setFromOwner(fromOwner);
            movement.setToOwner(toOwner);
            items.forEach(i -> i.setOwner(toOwner));
        }
        if (movementNewDto.getFromClientId() != null && movementNewDto.getToClientId() != null) {
            User fromClient = baseUserService.getUser(user.getOrganization(), movementNewDto.getFromClientId());
            baseItemService.checkItemsByClient(items, fromClient);
            User toClient = baseUserService.getUser(user.getOrganization(), movementNewDto.getToClientId());
            movement.setFromClient(fromClient);
            movement.setToClient(toClient);
            items.forEach(i -> i.setClient(toClient));
        }
        if (movementNewDto.getFromDepartmentId() != null && movementNewDto.getToDepartmentId() != null) {
            Department fromDepartment = baseDepartmentService.getDepartment(user.getOrganization(), movementNewDto.getFromDepartmentId());
            baseItemService.checkItemsByDepartment(items, fromDepartment);
            Department toDepartment = baseDepartmentService.getDepartment(user.getOrganization(), movementNewDto.getToDepartmentId());
            movement.setFromDepartment(fromDepartment);
            movement.setToDepartment(toDepartment);
            items.forEach(i -> i.setDepartment(toDepartment));
        }
        baseItemService.saveAllItems(items);
        movement.setItems(items);
        movement = baseMovementService.saveMovement(movement);
        return MovementMapper.toDto(movement);
    }

    @Transactional
    public List<MovementDto> getMovements(Long userId,
                                          Long fromOwnerId, Long toOwnerId,
                                          Long fromClientId, Long toClientId,
                                          Long fromDepartmentId, Long toDepartmentId,
                                          LocalDateTime fromDate, LocalDateTime toDate,
                                          int from, int size) {
        User user = baseUserService.getUser(userId);
        Predicate predicate = makePredicateByParams(user.getOrganization(),
                fromOwnerId, toOwnerId,
                fromClientId, toClientId,
                fromDepartmentId, toDepartmentId,
                fromDate, toDate);
        List<Movement> movements = baseMovementService.getMovements(predicate, from, size);
        return movements.stream()
                .map(MovementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovementDto getMovement(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        Movement movement = baseMovementService.getMovement(user.getOrganization(), id);
        return MovementMapper.toDto(movement);
    }

    private void checkFromAndToParams(MovementNewDto movementNewDto) {
        if (movementNewDto.getFromOwnerId() == null && movementNewDto.getToOwnerId() == null
                && movementNewDto.getFromClientId() == null && movementNewDto.getToClientId() == null
                && movementNewDto.getFromDepartmentId() == null && movementNewDto.getToDepartmentId() == null) {
            throw new IllegalArgumentException(FROM_AND_TO_MUST_BE_NOT_NULL);
        }
        if (movementNewDto.getFromOwnerId() == null ^ movementNewDto.getToOwnerId() == null) {
            throw new IllegalArgumentException(FROM_AND_TO_MUST_BE_NOT_NULL);
        }
        if (movementNewDto.getFromClientId() == null ^ movementNewDto.getToClientId() == null) {
            throw new IllegalArgumentException(FROM_AND_TO_MUST_BE_NOT_NULL);
        }
        if (movementNewDto.getFromDepartmentId() == null ^ movementNewDto.getToDepartmentId() == null) {
            throw new IllegalArgumentException(FROM_AND_TO_MUST_BE_NOT_NULL);
        }
    }

    private Predicate makePredicateByParams(Organization organization,
                                            Long fromOwnerId, Long toOwnerId,
                                            Long fromClientId, Long toClientId,
                                            Long fromDepartmentId, Long toDepartmentId,
                                            LocalDateTime fromDate, LocalDateTime toDate) {
        QMovement qMovement = QMovement.movement;
        Predicate predicate = qMovement.fromOwner.organization.eq(organization);
        if (fromOwnerId != null) {
            User fromOwner = baseUserService.getUser(organization, fromOwnerId);
            baseUserService.checkUserByReporting(fromOwner);
            predicate = qMovement.fromOwner.eq(fromOwner).and(predicate);
        }
        if (toOwnerId != null) {
            User toOwner = baseUserService.getUser(organization, toOwnerId);
            baseUserService.checkUserByReporting(toOwner);
            predicate = qMovement.toOwner.eq(toOwner).and(predicate);
        }
        if (fromClientId != null) {
            User fromClient = baseUserService.getUser(organization, fromClientId);
            predicate = qMovement.fromClient.eq(fromClient).and(predicate);
        }
        if (toClientId != null) {
            User toClient = baseUserService.getUser(organization, toClientId);
            predicate = qMovement.toClient.eq(toClient).and(predicate);
        }
        if (fromDepartmentId != null) {
            Department fromDepartment = baseDepartmentService.getDepartment(organization, fromDepartmentId);
            predicate = qMovement.fromDepartment.eq(fromDepartment).and(predicate);
        }
        if (toDepartmentId != null) {
            Department toDepartment = baseDepartmentService.getDepartment(organization, toDepartmentId);
            predicate = qMovement.toDepartment.eq(toDepartment).and(predicate);
        }
        if (fromDate != null) {
            predicate = qMovement.movementDate.after(fromDate).and(predicate);
        }
        if (toDate != null) {
            predicate = qMovement.movementDate.before(toDate).and(predicate);
        }
        return predicate;
    }
}
