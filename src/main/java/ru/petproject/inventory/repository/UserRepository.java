package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByOrganizationAndName(Organization organization, String name);
    boolean existsByEmailAndIdNot(String email, Long notThisId);
    boolean existsByOrganizationAndNameAndIdNot(Organization organization, String name, Long notThisId);
    Optional<User> findByOrganizationAndId(Organization organization, Long userId);
    List<User> findAllByOrganization(Organization organization);
    List<User> findByOrganizationAndIdIn(Organization organization, List<Long> usersId);
}
