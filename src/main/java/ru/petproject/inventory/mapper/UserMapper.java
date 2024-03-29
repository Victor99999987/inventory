package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.dto.UserShortDto;
import ru.petproject.inventory.model.User;

@UtilityClass
public class UserMapper {
    public static UserDto toDto(User user) {
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

    public static UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}
