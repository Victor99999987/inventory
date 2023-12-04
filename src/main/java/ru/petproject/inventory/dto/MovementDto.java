package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.petproject.inventory.model.Department;
import ru.petproject.inventory.model.Item;
import ru.petproject.inventory.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime movementDate;
    private UserDto fromOwner;
    private UserDto toOwner;
    private UserDto fromUser;
    private UserDto toUser;
    private DepartmentDto fromDepartment;
    private DepartmentDto toDepartment;
    private String description;
    private Set<ItemDto> items = new HashSet<>();
}
