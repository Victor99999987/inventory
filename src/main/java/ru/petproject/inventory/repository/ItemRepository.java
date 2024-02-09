package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.petproject.inventory.model.*;

import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    @Query("SELECT 1 FROM Item AS it " +
            "INNER JOIN Category AS ct ON it.category.id=ct.id " +
            "INNER JOIN Organization AS og ON ct.organization.id=og.id " +
            "WHERE og=:organization AND it.invNumber=:invNumber ")
    boolean existsByOrganizationAndInvNumber(Organization organization, String invNumber);

    @Query("SELECT it FROM Item AS it " +
            "INNER JOIN Category AS ct ON it.category.id=ct.id " +
            "INNER JOIN Organization AS og ON ct.organization.id=og.id " +
            "WHERE og=:organization AND it.id=:itemId ")
    Optional<Item> findByOrganizationAndId(Organization organization, Long itemId);

    @Query("SELECT 1 FROM Item AS it " +
            "INNER JOIN Category AS ct ON it.category.id=ct.id " +
            "INNER JOIN Organization AS og ON ct.organization.id=og.id " +
            "WHERE og=:organization AND it.invNumber=:invNumber AND it.id<>:notThisId ")
    boolean existsByOrganizationAndInvNumberAndIdNot(Organization organization, String invNumber, Long notThisId);

    @Query("SELECT it FROM Item AS it " +
            "INNER JOIN Category AS ct ON it.category.id=ct.id " +
            "INNER JOIN Organization AS og ON ct.organization.id=og.id " +
            "WHERE og=:organization AND it.id IN (:itemsId) ")
    Set<Item> findAllByOrganizationAndIdIn(Organization organization, Set<Long> itemsId);

    boolean existsByCategory(Category category);

    boolean existsByDepartment(Department department);

    boolean existsByOwner(User owner);

    boolean existsByOwnerOrUser(User owner, User user);
}
