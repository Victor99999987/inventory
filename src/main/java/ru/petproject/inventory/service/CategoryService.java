package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.CategoryNewDto;
import ru.petproject.inventory.dto.CategoryUpdateDto;
import ru.petproject.inventory.mapper.CategoryMapper;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final BaseUserService baseUserService;
    private final BaseCategoryService baseCategoryService;
    private final BaseItemService baseItemService;

    @Transactional
    public CategoryDto postCategory(Long userId, CategoryNewDto categoryNewDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        baseCategoryService.checkExistsCategory(user.getOrganization(), categoryNewDto.getName());
        Category category = Category.builder()
                .name(categoryNewDto.getName())
                .organization(user.getOrganization())
                .build();
        category = baseCategoryService.saveCategory(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public CategoryDto patchCategory(Long userId, Long id, CategoryUpdateDto categoryUpdateDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Category category = baseCategoryService.getCategory(user.getOrganization(), id);
        baseCategoryService.checkExistsCategory(user.getOrganization(), categoryUpdateDto.getName(), id);
        category.setName(categoryUpdateDto.getName());
        category = baseCategoryService.saveCategory(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public void deleteCategory(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Category category = baseCategoryService.getCategory(user.getOrganization(), id);
        baseItemService.checkExistsItem(category);
        baseCategoryService.deleteCategory(category);
    }

    @Transactional
    public List<CategoryDto> getCategories(Long userId) {
        User user = baseUserService.getUser(userId);
        return baseCategoryService.getCategories(user.getOrganization()).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto getCategory(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        Category category = baseCategoryService.getCategory(user.getOrganization(), id);
        return CategoryMapper.toDto(category);
    }
}
