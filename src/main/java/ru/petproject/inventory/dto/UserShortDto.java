package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Role;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;
    private String position;
}
