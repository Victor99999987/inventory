package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Organization;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCategoryDto {
    @NotBlank(message = "поле name должно быть указано и не должно состоять из пробелов")
    @Size(min = 1, max = 100, message = "поле name должно содержать от 1 до 100 символов")
    private String name;
}
