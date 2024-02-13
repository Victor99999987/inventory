package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.petproject.inventory.model.*;

import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    boolean existsByCategory_OrganizationAndInvNumber(Organization organization, String invNumber);
    Optional<Item> findByCategory_OrganizationAndId(Organization organization, Long id);
    boolean existsByCategory_OrganizationAndInvNumberAndIdNot(Organization organization, String invNumber, Long exceptThisId);
    Set<Item> findAllByCategory_OrganizationAndIdIn(Organization organization, Set<Long> ids);
    boolean existsByCategory(Category category);
    boolean existsByDepartment(Department department);
    boolean existsByOwner(User owner);
    boolean existsByOwnerOrClient(User owner, User client);
}
