package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long id;

    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    private Boolean reporting;

    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String position;

    @Email(message = INVALID_EMAIL)
    @Size(min = 6, max = 100, message = FIELD_MUST_BE_FROM_6_TO_100)
    private String email;

    private Role role;
}
