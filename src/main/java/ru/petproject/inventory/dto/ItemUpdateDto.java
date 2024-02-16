package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;
import static ru.petproject.inventory.common.ValidationMessage.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDto {
    @NotNull(message = FIELD_MUST_BE_NOT_NULL)
    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long id;

    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String name;

    @Size(min = 1, max = 1000, message = FIELD_MUST_BE_FROM_1_TO_1000)
    private String description;

    @Positive(message = FIELD_MUST_BE_POSITIVE)
    private Long categoryId;

    private Boolean serviceable;

    @Size(min = 1, max = 100, message = FIELD_MUST_BE_FROM_1_TO_100)
    private String invNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
