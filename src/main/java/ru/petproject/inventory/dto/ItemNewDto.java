package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
public class ItemNewDto {
    @NotBlank(message = "поле name должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    @NotBlank(message = "поле description должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 1000, message = "поле description должно содержать от 1 до 1000 символов")
    private String description;

    @NotNull(message = "поле categoryId должно быть указано")
    @Positive(message = "поле categoryId должно быть положительным")
    private Long categoryId;

    @NotNull(message = "поле serviceable должно быть указано")
    private Boolean serviceable;

    @Size(min = 1, max = 50, message = "поле invNumber должно содержать от 1 до 50 символов")
    private String invNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
