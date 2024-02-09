package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementNewDto {
    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long fromOwnerId;

    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long toOwnerId;

    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long fromUserId;

    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long toUserId;

    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long fromDepartmentId;

    //@NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long toDepartmentId;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Size(min = 1, max = 1000, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Size(min = 1, message = ValidationMessage.SET_MIN_SIZE_MUST_BE_1)
    private Set<Long> itemsId;
}
