package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = "поле id должно быть указано")
    @Positive(message = "поле id должно быть положительным")
    private Long id;

    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;

    private Boolean reporting;

    @Size(min = 1, max = 100, message = "поле position должно содержать от 1 до 50 символов")
    private String position;

    @Email(message = "неверный формат email")
    @Size(min = 6, max = 50, message = "поле email должно быть от 6 до 50 символов")
    private String email;

    private Role role;
}
