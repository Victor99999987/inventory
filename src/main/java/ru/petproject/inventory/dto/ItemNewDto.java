package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.petproject.inventory.common.ValidationMessage;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemNewDto {
    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @NotBlank(message = ValidationMessage.FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 1000, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long categoryId;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long userId;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long ownerId;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    @Positive(message = ValidationMessage.FIELD_MUST_BE_POSITIVE)
    private Long departmentId;

    @NotNull(message = ValidationMessage.FIELD_MUST_BE_NOT_NULL)
    private Boolean serviceable;

    @Size(min = 1, max = 100, message = ValidationMessage.FIELD_MUST_BE_FROM_1_TO_100)
    private String invNumber;

    @FutureOrPresent(message = ValidationMessage.DATE_MUST_BE_FUTURE_OR_PRESENT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
