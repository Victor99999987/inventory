package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.model.Role;

import javax.persistence.*;

public class UserDto {
    private Long id;
    private String name;
    private boolean reporting;
    private String position;
    private String email;
    private Role role;
    private OrganizationDto organization;
}
