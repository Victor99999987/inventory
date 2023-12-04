package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {
}
