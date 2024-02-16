package ru.petproject.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.petproject.inventory.common.ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100;
import static ru.petproject.inventory.common.ValidationMessage.FIELD_MUST_BE_NOT_BLANK;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentNewDto {
    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String address;
}
