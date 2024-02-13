package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementUpdateDto {
    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long id;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromOwnerId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toOwnerId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromClientId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toClientId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromDepartmentId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toDepartmentId;

    @Size(min = 1, max = 1000, message = FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    private Set<Long> itemsId;
}
