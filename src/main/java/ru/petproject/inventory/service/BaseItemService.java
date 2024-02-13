package ru.petproject.inventory.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.repository.ItemRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BaseItemService {
    private static final String ITEM_NOT_FOUND = "Оборудование с id %d не найдено";
    private static final String INV_NUMBER_ALREADY_EXISTS = "Оборудование с инв.№ %s уже существует";
    private static final String ITEM_WITH_CATEGORY_ALREADY_EXISTS = "Оборудование, связанное с категорией %s существует";
    private static final String ITEM_WITH_DEPARTMENT_ALREADY_EXISTS = "Оборудование, связанное с подразделением %s существует";
    private static final String ITEM_WITH_OWNER_ALREADY_EXISTS = "Оборудование, находящееся в подотчете у %s существует";
    private static final String ITEM_WITH_USER_ALREADY_EXISTS = "У пользователя %s есть оборудование";
    private final ItemRepository itemRepository;

    public Item getItem(Organization organization, Long id) {
        return itemRepository.findByCategory_OrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(ITEM_NOT_FOUND, id)));
    }

    public List<Item> getItems(Predicate predicate, int from, int size){
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        return itemRepository.findAll(predicate, pageable).getContent();
    }

    public Set<Item> getItems(Organization organization, Set<Long> ids){
        return itemRepository.findAllByCategory_OrganizationAndIdIn(organization, ids);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> saveAllItems(Set<Item> items) {
        return itemRepository.saveAll(items);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    public void checkExistsItem(Category category) {
        if (itemRepository.existsByCategory(category)) {
            throw new AlreadyExistsException(String.format(ITEM_WITH_CATEGORY_ALREADY_EXISTS, category.getName()));
        }
    }

    public void checkExistsItem(Department department) {
        if (itemRepository.existsByDepartment(department)) {
            throw new AlreadyExistsException(String.format(ITEM_WITH_DEPARTMENT_ALREADY_EXISTS, department.getName()));
        }
    }

    public void checkExistsItem(Organization organization, String invNumber) {
        if (itemRepository.existsByCategory_OrganizationAndInvNumber(organization, invNumber)) {
            throw new AlreadyExistsException(String.format(INV_NUMBER_ALREADY_EXISTS, invNumber));
        }
    }

    public void checkExistsItemByOwner(User owner) {
        if (itemRepository.existsByOwner(owner)) {
            throw new AlreadyExistsException(String.format(ITEM_WITH_OWNER_ALREADY_EXISTS, owner.getName()));
        }
    }

    public void checkExistsItemByOwnerOrClient(User user) {
        if (itemRepository.existsByOwnerOrClient(user, user)) {
            throw new AlreadyExistsException(String.format(ITEM_WITH_USER_ALREADY_EXISTS, user.getName()));
        }
    }

    public void checkExistsItem(Organization organization, String invNumber, Long exceptThisId) {
        if (itemRepository.existsByCategory_OrganizationAndInvNumberAndIdNot(organization, invNumber, exceptThisId)) {
            throw new AlreadyExistsException(String.format(INV_NUMBER_ALREADY_EXISTS, invNumber));
        }
    }

    public void checkItemsByIds(Set<Item> items, Set<Long> ids) {
        if (items.size() != ids.size()) {
            throw new NotFoundException("Часть оборудования не совпадает со списком id");
        }
        if (items.stream().anyMatch(i -> !ids.contains(i.getId()))) {
            throw new NotFoundException("Часть оборудования не совпадает со списком id");
        }
    }

    public void checkItemsByOwner(Set<Item> items, User owner) {
        if (items.stream().anyMatch(i -> !Objects.equals(i.getOwner(), owner))) {
            throw new IllegalArgumentException("Оборудование не находится у подотчетного лица");
        }
    }

    public void checkItemsByClient(Set<Item> items, User client) {
        if (items.stream().anyMatch(i -> !Objects.equals(i.getClient(), client))) {
            throw new IllegalArgumentException("Оборудование не находится у пользователя");
        }
    }

    public void checkItemsByDepartment(Set<Item> items, Department department) {
        if (items.stream().anyMatch(i -> !Objects.equals(i.getDepartment(), department))) {
            throw new IllegalArgumentException("Оборудование не находится в подразделении");
        }
    }
}
