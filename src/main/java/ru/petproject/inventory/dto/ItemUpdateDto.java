package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDto {
    @NotNull(message = "поле id должно быть указано")
    @Positive(message = "поле id должно быть положительным")
    private Long id;

    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    @Size(min = 1, max = 1000, message = "поле description должно содержать от 1 до 1000 символов")
    private String description;

    private Long categoryId;

    private Boolean serviceable;

    @Size(min = 1, max = 50, message = "поле invNumber должно содержать от 1 до 50 символов")
    private String invNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
