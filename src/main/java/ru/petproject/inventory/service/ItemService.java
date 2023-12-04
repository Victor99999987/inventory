package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.ItemNewDto;
import ru.petproject.inventory.dto.ItemUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.ItemMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.repository.CategoryRepository;
import ru.petproject.inventory.repository.ItemRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ItemDto postItem(Long userId, ItemNewDto itemNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        Category category = getCategory(user.getOrganization(), itemNewDto.getCategoryId());
        Item item = Item.builder()
                .name(itemNewDto.getName())
                .description(itemNewDto.getDescription())
                .category(category)
                .serviceable(itemNewDto.getServiceable())
                .created(LocalDateTime.now())
                .build();
        if (itemNewDto.getInvNumber() != null) {
            checkBlank("invNumber", itemNewDto.getInvNumber());
            checkExistsItem(user.getOrganization(), itemNewDto.getInvNumber());
            item.setInvNumber(itemNewDto.getInvNumber());
        }
        if (itemNewDto.getFinished() != null) {
            checkPast("finished", itemNewDto.getFinished());
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
        if(itemUpdateDto.getName()!=null){
            checkBlank("name", itemUpdateDto.getName());
            item.setName(itemUpdateDto.getName());
        }
        if(itemUpdateDto.getDescription()!=null){
            checkBlank("description", itemUpdateDto.getDescription());
            item.setDescription(itemUpdateDto.getDescription());
        }
        if(itemUpdateDto.getCategoryId()!=null){
            Category category = getCategory(user.getOrganization(), itemUpdateDto.getCategoryId());
            item.setCategory(category);
        }
        if(itemUpdateDto.getServiceable()!=null){
            item.setServiceable(itemUpdateDto.getServiceable());
        }
        if(itemUpdateDto.getInvNumber()!=null){
            checkBlank("invNumber", itemUpdateDto.getInvNumber());
            checkExistsItem(user.getOrganization(), itemUpdateDto.getInvNumber(), id);
            item.setInvNumber(itemUpdateDto.getInvNumber());
        }
        if (itemUpdateDto.getFinished() != null) {
            checkPast("finished", itemUpdateDto.getFinished());
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
        itemRepository.delete(item);
    }



    private User getUser(Long userId) {
        return userRepository.findById(userId)
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

    private void checkPast(String name, LocalDateTime value) {
        if (value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Поле " + name + " не может быть в прошлом");
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
}
