package ru.petproject.inventory.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNewDto {
    @NotBlank(message = "поле userName должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле userName должно содержать от 1 до 100 символов")
    private String userName;

    @NotBlank(message = "поле position должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 50, message = "поле position должно содержать от 1 до 50 символов")
    private String position;

    @NotEmpty(message = "поле email должно быть указано")
    @Email(message = "неверный формат email")
    @Size(min = 6, max = 50, message = "поле email должно быть от 6 до 50 символов")
    private String email;

    @NotBlank(message = "поле organizationName должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле organizationName должно содержать от 1 до 100 символов")
    private String organizationName;
}
