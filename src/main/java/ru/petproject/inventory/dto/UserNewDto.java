package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.*;

import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNewDto {
    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    private Boolean reporting;

    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String position;

    @NotEmpty(message = FIELD_MUST_BE_NOT_EMPTY)
    @Email(message = INVALID_EMAIL)
    @Size(min = 6, max = 100, message = FIELD_MUST_BE_FROM_6_TO_100)
    private String email;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    private Role role;
}
