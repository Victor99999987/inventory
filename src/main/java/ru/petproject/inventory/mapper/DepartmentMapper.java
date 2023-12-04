package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.Department;

@UtilityClass
public class DepartmentMapper {
    public static DepartmentDto toDto(Department department){
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .address(department.getAddress())
                .build();
    }
}
