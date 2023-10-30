package ru.petproject.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.petproject.inventory.model.Item;
import ru.petproject.inventory.model.User;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
