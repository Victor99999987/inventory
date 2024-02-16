package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.Organization;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrganization(Organization organization);

    Optional<Category> findByOrganizationAndId(Organization organization, Long Id);

    boolean existsByOrganizationAndName(Organization organization, String name);

    boolean existsByOrganizationAndNameAndIdNot(Organization organization, String name, Long exceptThisId);
}
