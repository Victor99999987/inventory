package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNewDto {
    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String userName;

    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String position;

    @NotEmpty(message = ValidationMessage.FIELD_MUST_BE_NOT_EMPTY)
    @Email(message = ValidationMessage.INVALID_EMAIL)
    @Size(min = 6, max = 1000, message = ValidationMessage.FIELD_MUST_BE_FROM_6_TO_100)
    private String email;

    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String organizationName;
}
