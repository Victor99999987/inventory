package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByOrganizationAndName(Organization organization, String name);

    boolean existsByEmailAndIdNot(String email, Long exceptThisId);

    boolean existsByOrganizationAndNameAndIdNot(Organization organization, String name, Long exceptThisId);

    Optional<User> findByOrganizationAndId(Organization organization, Long userId);

    List<User> findAllByOrganization(Organization organization);

    List<User> findByOrganizationAndIdIn(Organization organization, List<Long> usersId);

    boolean existsByOrganizationAndRoleAndIdNot(Organization organization, Role role, Long exceptThisId);
}
