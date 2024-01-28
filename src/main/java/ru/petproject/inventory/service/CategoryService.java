package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.CategoryNewDto;
import ru.petproject.inventory.dto.CategoryUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.CategoryMapper;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.repository.CategoryRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoryDto postCategory(Long userId, CategoryNewDto categoryNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        checkExistsCategory(user.getOrganization(), categoryNewDto.getName());
        Category category = Category.builder()
                .name(categoryNewDto.getName())
                .organization(user.getOrganization())
                .build();
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public CategoryDto patchCategory(Long userId, Long id, CategoryUpdateDto categoryUpdateDto) {
        User user = getUser(userId);
        checkAccess(user);
        Category category = getCategory(user.getOrganization(), id);
        checkExistsCategory(user.getOrganization(), categoryUpdateDto.getName(), id);
        category.setName(categoryUpdateDto.getName());
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public void deleteCategory(Long userId, Long id) {
        User user = getUser(userId);
        checkAccess(user);
        Category category = getCategory(user.getOrganization(), id);
        //нужно глянуть, может есть оборудование в этой категории
        categoryRepository.delete(category);
    }

    @Transactional
    public List<CategoryDto> getCategories(Long userId) {
        User user = getUser(userId);
        return categoryRepository.findAllByOrganization(user.getOrganization()).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }

    private Category getCategory(Organization organization, Long categoryId) {
        return categoryRepository.findByOrganizationAndId(organization, categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с id %d не найдена", categoryId)));
    }


    private void checkExistsCategory(Organization organization, String name) {
        if (categoryRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format("Категория с name %s уже существует", name));
        }
    }

    private void checkExistsCategory(Organization organization, String name, Long notThisId) {
        if (categoryRepository.existsByOrganizationAndNameAndIdNot(organization, name, notThisId)) {
            throw new AlreadyExistsException(String.format("Категория с name %s уже существует", name));
        }
    }
}
