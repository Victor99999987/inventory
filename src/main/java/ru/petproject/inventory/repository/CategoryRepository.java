package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
