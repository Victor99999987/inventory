package ru.petproject.inventory.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.ItemNewDto;
import ru.petproject.inventory.dto.ItemUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.ItemMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.repository.CategoryRepository;
import ru.petproject.inventory.repository.DepartmentRepository;
import ru.petproject.inventory.repository.ItemRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public ItemDto postItem(Long userId, ItemNewDto itemNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        Category category = getCategory(user.getOrganization(), itemNewDto.getCategoryId());
        User userFact = getUser(user.getOrganization(), itemNewDto.getUserId());
        User owner = getUser(user.getOrganization(), itemNewDto.getOwnerId());
        Department department = getDepartment(user.getOrganization(), itemNewDto.getDepartmentId());
        Item item = Item.builder()
                .name(itemNewDto.getName())
                .description(itemNewDto.getDescription())
                .category(category)
                .user(userFact)
                .owner(owner)
                .department(department)
                .serviceable(itemNewDto.getServiceable())
                .created(LocalDateTime.now())
                .build();
        if (itemNewDto.getInvNumber() != null) {
            checkBlank("invNumber", itemNewDto.getInvNumber());
            checkExistsItem(user.getOrganization(), itemNewDto.getInvNumber());
            item.setInvNumber(itemNewDto.getInvNumber());
        }
        if (itemNewDto.getFinished() != null) {
            item.setFinished(itemNewDto.getFinished());
        }
        item = itemRepository.save(item);
        return ItemMapper.toDto(item);
    }

    @Transactional
    public ItemDto patchItem(Long userId, Long id, ItemUpdateDto itemUpdateDto) {
        User user = getUser(userId);
        checkAccess(user);
        Item item = getItem(user.getOrganization(), id);
        if (itemUpdateDto.getName() != null) {
            checkBlank("name", itemUpdateDto.getName());
            item.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            checkBlank("description", itemUpdateDto.getDescription());
            item.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getCategoryId() != null) {
            Category category = getCategory(user.getOrganization(), itemUpdateDto.getCategoryId());
            item.setCategory(category);
        }
        if (itemUpdateDto.getServiceable() != null) {
            item.setServiceable(itemUpdateDto.getServiceable());
        }
        if (itemUpdateDto.getInvNumber() != null) {
            checkBlank("invNumber", itemUpdateDto.getInvNumber());
            checkExistsItem(user.getOrganization(), itemUpdateDto.getInvNumber(), id);
            item.setInvNumber(itemUpdateDto.getInvNumber());
        }
        if (itemUpdateDto.getFinished() != null) {
            checkFinishedIsBeforeCreated(itemUpdateDto.getFinished(), item.getCreated());
            item.setFinished(itemUpdateDto.getFinished());
        }
        item = itemRepository.save(item);
        return ItemMapper.toDto(item);
    }

    @Transactional
    public void deleteItem(Long userId, Long id) {
        User user = getUser(userId);
        checkAccess(user);
        Item item = getItem(user.getOrganization(), id);
        //нужно глянуть связанные перемещения
        //так вроде и так удаляться лишние связи из таблицы items_movements
        //может только косяк быть с перемещениями в которых нет ничего
        itemRepository.delete(item);
    }

    @Transactional
    public List<ItemDto> getItems(Long userId, String name, Long categoryId, Boolean serviceable, String invNumber,
                                  Long clientId, Long ownerId, Long departmentId, int from, int size) {
        User user = getUser(userId);
        QItem qItem = QItem.item;
        Predicate predicate = qItem.category.organization.eq(user.getOrganization());
        if (name != null) {
            predicate = qItem.name.containsIgnoreCase(name).and(predicate);
        }
        if (categoryId != null) {
            Category category = getCategory(user.getOrganization(), categoryId);
            predicate = qItem.category.eq(category).and(predicate);
        }
        if (serviceable != null) {
            predicate = qItem.serviceable.eq(serviceable).and(predicate);
        }
        if (invNumber != null) {
            predicate = qItem.invNumber.containsIgnoreCase(invNumber).and(predicate);
        }
        if (clientId != null) {
            User client = getUser(user.getOrganization(), clientId);
            predicate = qItem.user.eq(client).and(predicate);
        }
        if (ownerId != null) {
            User owner = getUser(user.getOrganization(), ownerId);
            predicate = qItem.owner.eq(owner).and(predicate);
        }
        if (departmentId != null) {
            Department department = getDepartment(user.getOrganization(), departmentId);
            predicate = qItem.department.eq(department).and(predicate);
        }
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Item> items = itemRepository.findAll(predicate, pageable).getContent();
        return items.stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto getItem(Long userId, Long id) {
        User user = getUser(userId);
        Item item = getItem(user.getOrganization(), id);
        return ItemMapper.toDto(item);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private User getUser(Organization organization, Long userId) {
        return userRepository.findByOrganizationAndId(organization, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private Category getCategory(Organization organization, Long categoryId) {
        return categoryRepository.findByOrganizationAndId(organization, categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с id %d не найдена", categoryId)));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }

    private void checkBlank(String name, String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле " + name + " не может состоять из пробелов");
        }
    }

    private void checkFinishedIsBeforeCreated(LocalDateTime finished, LocalDateTime created) {
        if (finished.isBefore(created)) {
            throw new IllegalArgumentException("Дата окончания эксплуатации не может быть раньше начала");
        }
    }

    private void checkExistsItem(Organization organization, String invNumber) {
        if (itemRepository.existsByOrganizationAndInvNumber(organization, invNumber)) {
            throw new AlreadyExistsException(String.format("Оборудование с invNumber %s уже существует", invNumber));
        }
    }

    private void checkExistsItem(Organization organization, String invNumber, long notThisId) {
        if (itemRepository.existsByOrganizationAndInvNumberAndIdNot(organization, invNumber, notThisId)) {
            throw new AlreadyExistsException(String.format("Оборудование с invNumber %s уже существует", invNumber));
        }
    }

    private Item getItem(Organization organization, Long itemId) {
        return itemRepository.findByOrganizationAndId(organization, itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Оборудование с id %d не найдено", itemId)));
    }

    private Department getDepartment(Organization organization, Long departmentId) {
        return departmentRepository.findByOrganizationAndId(organization, departmentId)
                .orElseThrow(() -> new NotFoundException(String.format("Подразделение с id %d не найдено", departmentId)));
    }

}
