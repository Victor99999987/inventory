package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNewDto {
    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    private Boolean reporting;

    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String position;

    @NotEmpty(message = ValidationMessage.FIELD_MUST_BE_NOT_EMPTY)
    @Email(message = ValidationMessage.INVALID_EMAIL)
    @Size(min = 6, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_6_TO_100)
    private String email;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    private Role role;
}
