package ru.petproject.inventory.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.ItemNewDto;
import ru.petproject.inventory.dto.ItemUpdateDto;
import ru.petproject.inventory.mapper.ItemMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.service.base.BaseCategoryService;
import ru.petproject.inventory.service.base.BaseDepartmentService;
import ru.petproject.inventory.service.base.BaseItemService;
import ru.petproject.inventory.service.base.BaseUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final BaseUserService baseUserService;
    private final BaseItemService baseItemService;
    private final BaseCategoryService baseCategoryService;
    private final BaseDepartmentService baseDepartmentService;

    @Transactional
    public ItemDto postItem(Long userId, ItemNewDto itemNewDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Category category = baseCategoryService.getCategory(user.getOrganization(), itemNewDto.getCategoryId());
        User client = baseUserService.getUser(user.getOrganization(), itemNewDto.getClientId());
        User owner = baseUserService.getUser(user.getOrganization(), itemNewDto.getOwnerId());
        Department department = baseDepartmentService.getDepartment(user.getOrganization(), itemNewDto.getDepartmentId());
        Item item = Item.builder()
                .name(itemNewDto.getName())
                .description(itemNewDto.getDescription())
                .category(category)
                .client(client)
                .owner(owner)
                .department(department)
                .serviceable(itemNewDto.getServiceable())
                .created(LocalDateTime.now())
                .build();
        if (itemNewDto.getInvNumber() != null) {
            Utility.checkBlank("invNumber", itemNewDto.getInvNumber());
            baseItemService.checkExistsItem(user.getOrganization(), itemNewDto.getInvNumber());
            item.setInvNumber(itemNewDto.getInvNumber());
        }
        if (itemNewDto.getFinished() != null) {
            item.setFinished(itemNewDto.getFinished());
        }
        item = baseItemService.saveItem(item);
        return ItemMapper.toDto(item);
    }

    @Transactional
    public ItemDto patchItem(Long userId, Long id, ItemUpdateDto itemUpdateDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Item item = baseItemService.getItem(user.getOrganization(), id);
        if (itemUpdateDto.getName() != null) {
            Utility.checkBlank("name", itemUpdateDto.getName());
            item.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            Utility.checkBlank("description", itemUpdateDto.getDescription());
            item.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getCategoryId() != null) {
            Category category = baseCategoryService.getCategory(user.getOrganization(), itemUpdateDto.getCategoryId());
            item.setCategory(category);
        }
        if (itemUpdateDto.getServiceable() != null) {
            item.setServiceable(itemUpdateDto.getServiceable());
        }
        if (itemUpdateDto.getInvNumber() != null) {
            Utility.checkBlank("invNumber", itemUpdateDto.getInvNumber());
            baseItemService.checkExistsItem(user.getOrganization(), itemUpdateDto.getInvNumber(), id);
            item.setInvNumber(itemUpdateDto.getInvNumber());
        }
        if (itemUpdateDto.getFinished() != null) {
            Utility.checkFinishedIsBeforeCreated(itemUpdateDto.getFinished(), item.getCreated());
            item.setFinished(itemUpdateDto.getFinished());
        }
        item = baseItemService.saveItem(item);
        return ItemMapper.toDto(item);
    }

    @Transactional
    public void deleteItem(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Item item = baseItemService.getItem(user.getOrganization(), id);
        baseItemService.deleteItem(item);
    }

    @Transactional
    public List<ItemDto> getItems(Long userId, String name, Long categoryId, Boolean serviceable, String invNumber,
                                  Long clientId, Long ownerId, Long departmentId, int from, int size) {
        User user = baseUserService.getUser(userId);
        Predicate predicate = makePredicateByParams(user.getOrganization(), name, categoryId, serviceable, invNumber,
                clientId, ownerId, departmentId);
        List<Item> items = baseItemService.getItems(predicate, from, size);
        return items.stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto getItem(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        Item item = baseItemService.getItem(user.getOrganization(), id);
        return ItemMapper.toDto(item);
    }

    private Predicate makePredicateByParams(Organization organization,
                                            String name, Long categoryId, Boolean serviceable, String invNumber,
                                            Long clientId, Long ownerId, Long departmentId) {
        QItem qItem = QItem.item;
        Predicate predicate = qItem.category.organization.eq(organization);
        if (name != null) {
            predicate = qItem.name.containsIgnoreCase(name).and(predicate);
        }
        if (categoryId != null) {
            Category category = baseCategoryService.getCategory(organization, categoryId);
            predicate = qItem.category.eq(category).and(predicate);
        }
        if (serviceable != null) {
            predicate = qItem.serviceable.eq(serviceable).and(predicate);
        }
        if (invNumber != null) {
            predicate = qItem.invNumber.containsIgnoreCase(invNumber).and(predicate);
        }
        if (clientId != null) {
            User client = baseUserService.getUser(organization, clientId);
            predicate = qItem.client.eq(client).and(predicate);
        }
        if (ownerId != null) {
            User owner = baseUserService.getUser(organization, ownerId);
            predicate = qItem.owner.eq(owner).and(predicate);
        }
        if (departmentId != null) {
            Department department = baseDepartmentService.getDepartment(organization, departmentId);
            predicate = qItem.department.eq(department).and(predicate);
        }
        return predicate;
    }
}
