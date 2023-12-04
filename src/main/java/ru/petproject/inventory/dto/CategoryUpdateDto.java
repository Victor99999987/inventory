package ru.petproject.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    @NotNull(message = "поле id должно быть указано")
    @Positive(message = "поле id должно быть положительным")
    private Long id;

    @NotBlank(message = "поле name должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;
}
