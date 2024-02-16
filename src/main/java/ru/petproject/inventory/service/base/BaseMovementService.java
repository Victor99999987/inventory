package ru.petproject.inventory.service.base;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.model.Movement;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.repository.MovementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseMovementService {
    private static final String MOVEMENT_NOT_FOUND = "Перемещение с id %d не найдено";
    private final MovementRepository movementRepository;

    public Movement getMovement(Organization organization, Long id) {
        return movementRepository.findByFromOwner_OrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(MOVEMENT_NOT_FOUND, id)));
    }

    public List<Movement> getMovements(Predicate predicate, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        return movementRepository.findAll(predicate, pageable).getContent();
    }

    public Movement saveMovement(Movement movement) {
        return movementRepository.save(movement);
    }
}
