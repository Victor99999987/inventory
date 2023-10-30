package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Organization;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private OrganizationDto organization;
}
