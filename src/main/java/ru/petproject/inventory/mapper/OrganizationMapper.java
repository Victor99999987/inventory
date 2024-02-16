package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.model.Organization;

@UtilityClass
public class OrganizationMapper {
    public static OrganizationDto toDto(Organization organization) {
        return OrganizationDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .build();
    }
}
