package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.model.Category;

@UtilityClass
public class CategoryMapper {
    public static CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
