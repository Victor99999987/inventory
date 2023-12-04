package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.dto.RegistrationDto;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.User;

@UtilityClass
public class RegistrationMapper {
    public static RegistrationDto toDto(User user){
        return RegistrationDto.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .organizationName(user.getOrganization().getName())
                .build();
    }
}
