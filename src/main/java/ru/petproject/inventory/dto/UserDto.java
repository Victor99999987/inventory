package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Role;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Boolean reporting;
    private String position;
    private String email;
    private Role role;
    private OrganizationDto organization;
}
