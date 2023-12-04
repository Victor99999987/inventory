package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;

@UtilityClass
public class UserMapper {
    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .reporting(user.isReporting())
                .position(user.getPosition())
                .email(user.getEmail())
                .role(user.getRole())
                .organization(OrganizationMapper.toDto(user.getOrganization()))
                .build();
    }
}
