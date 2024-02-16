package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
