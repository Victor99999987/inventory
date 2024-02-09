package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long id;

    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    private Boolean reporting;

    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String position;

    @Email(message = ValidationMessage.INVALID_EMAIL)
    @Size(min = 6, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_6_TO_100)
    private String email;

    private Role role;
}
