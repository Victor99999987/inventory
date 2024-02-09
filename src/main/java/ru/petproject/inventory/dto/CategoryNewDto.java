package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNewDto {
    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String name;
}
