package ru.petproject.inventory.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private String userName;
    private String email;
    private String organizationName;
}
