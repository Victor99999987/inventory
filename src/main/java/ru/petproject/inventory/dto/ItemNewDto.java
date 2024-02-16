package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;
import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemNewDto {
    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @NotBlank(message = FIELD_MUST_BE_NOT_BLANK)
    @Size(min = 1, max = 1000, message = FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long categoryId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long clientId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long ownerId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long departmentId;

    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    private Boolean serviceable;

    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String invNumber;

    @FutureOrPresent(message = DATE_MUST_BE_FUTURE_OR_PRESENT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
