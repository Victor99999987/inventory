package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUpdateDto {
    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long id;

    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String address;
}
