package ru.petproject.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentNewDto {
    @NotBlank(message = "поле name должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    @NotBlank(message = "поле address должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 200, message = "поле address должно содержать от 1 до 200 символов")
    private String address;
}
