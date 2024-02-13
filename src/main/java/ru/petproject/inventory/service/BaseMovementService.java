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
public class BaseMovementService {
    private static final String MOVEMENT_NOT_FOUND = "Перемещение с id %d не найдено";

    private final MovementRepository movementRepository;

    public Movement getMovement(Organization organization, Long id) {
        return movementRepository.findByFromOwner_OrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(MOVEMENT_NOT_FOUND, id)));
    }

    public List<Movement> getMovements(Predicate predicate, int from, int size){
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        return movementRepository.findAll(predicate, pageable).getContent();
    }

    public Movement saveMovement(Movement movement){
        return movementRepository.save(movement);
    }

    public void deleteMovement(Movement movement){
        movementRepository.delete(movement);
    }

//    private void checkItemsId(Set<Long> itemsId) {
//        if (itemsId.stream().anyMatch(Objects::isNull)) {
//            throw new IllegalArgumentException("Id перемещаемого оборудования должно быть указано");
//        }
//        if (itemsId.stream().anyMatch(id -> id <= 0)) {
//            throw new IllegalArgumentException("Id перемещаемого оборудования должно быть положительным");
//        }
//    }
//
//    private void checkExistsItems(Set<Long> itemsId, Set<Item> items) {
//        for (Long id : itemsId) {
//            if (items.stream()
//                    .map(Item::getId)
//                    .noneMatch(id::equals)) {
//                throw new NotFoundException(String.format("Оборудование с id %s не найдено", id));
//            }
//        }
//    }
//
//    private void checkExistsItems(User user, Set<Item> items) {
//        if (items.stream().noneMatch(i -> Objects.equals(i.getOwner().getId(), user.getId()))) {
//            throw new IllegalArgumentException("Оборудование не находится у пользователя");
//        }
//    }
//
//    private void checkFromAndToParams(MovementNewDto movementNewDto) {
//        if (movementNewDto.getFromOwnerId() == null && movementNewDto.getToOwnerId() == null
//                && movementNewDto.getFromUserId() == null && movementNewDto.getToUserId() == null
//                && movementNewDto.getFromDepartmentId() == null && movementNewDto.getToDepartmentId() == null) {
//            throw new IllegalArgumentException("Нужно указать откуда и куда переносить элемент");
//        }
//        if (movementNewDto.getFromOwnerId() == null ^ movementNewDto.getToOwnerId() == null) {
//            throw new IllegalArgumentException("Нужно указать owner откуда и куда переносить элемент");
//        }
//        if (movementNewDto.getFromUserId() == null ^ movementNewDto.getToUserId() == null) {
//            throw new IllegalArgumentException("Нужно указать user откуда и куда переносить элемент");
//        }
//        if (movementNewDto.getFromDepartmentId() == null ^ movementNewDto.getToDepartmentId() == null) {
//            throw new IllegalArgumentException("Нужно указать department откуда и куда переносить элемент");
//        }
//    }
}
