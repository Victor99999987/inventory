package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.User;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
