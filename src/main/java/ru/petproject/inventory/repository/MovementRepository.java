package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.petproject.inventory.model.Movement;
import ru.petproject.inventory.model.Organization;

import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long>, QuerydslPredicateExecutor<Movement> {
    Optional<Movement> findByFromOwner_OrganizationAndId(Organization organization, Long id);
}
