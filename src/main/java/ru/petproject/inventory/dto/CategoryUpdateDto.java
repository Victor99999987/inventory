package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long id;

    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;
}
