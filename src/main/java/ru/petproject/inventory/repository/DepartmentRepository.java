package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Department;
import ru.petproject.inventory.model.User;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
