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
public class DepartmentUpdateDto {
    @NotNull(message = "поле id должно быть указано")
    @Positive(message = "поле id должно быть положительным")
    private Long id;

    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    @Size(min = 1, max = 200, message = "поле address должно содержать от 1 до 200 символов")
    private String address;
}
