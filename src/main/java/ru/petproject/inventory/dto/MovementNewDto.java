package ru.petproject.inventory.dto;

import lombok.*;

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
public class MovementNewDto {
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromOwnerId;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toOwnerId;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromClientId;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toClientId;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long fromDepartmentId;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long toDepartmentId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Size(min = 1, max = 1000, message = FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Size(min = 1, message = SET_MIN_SIZE_MUST_BE_1)
    private Set<Long> itemsId;
}
