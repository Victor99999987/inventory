package ru.petproject.inventory.dto;

import lombok.*;
import ru.petproject.inventory.model.Department;
import ru.petproject.inventory.model.Item;
import ru.petproject.inventory.model.User;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementDto {
    private Long id;
    private LocalDateTime movingDate;
    private Item item;
    private User fromOwner;
    private User toOwner;
    private Department fromDepartment;
    private Department toDepartment;
    private String description;
}
