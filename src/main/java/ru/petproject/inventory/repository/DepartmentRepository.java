package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Department;
import ru.petproject.inventory.model.Organization;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByOrganizationAndId(Organization organization, Long departmentId);

    List<Department> findAllByOrganization(Organization organization);

    boolean existsByOrganizationAndName(Organization organization, String name);

    boolean existsByOrganizationAndNameAndIdNot(Organization organization, String name, Long exceptThisId);
}
