package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNewDto {
    @NotBlank(message = "поле name должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    @NotNull(message = "поле reporting должно быть указано")
    private Boolean reporting;

    @NotBlank(message = "поле position должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле position должно содержать от 1 до 50 символов")
    private String position;

    @NotEmpty(message = "поле email должно быть указано")
    @Email(message = "неверный формат email")
    @Size(min = 6, max = 50, message = "поле email должно быть от 6 до 50 символов")
    private String email;

    @NotNull(message = "поле role должно быть указано")
    private Role role;
}
