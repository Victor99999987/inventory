package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    private UserShortDto fromOwner;
    private UserShortDto toOwner;
    private UserShortDto fromClient;
    private UserShortDto toClient;
    private DepartmentDto fromDepartment;
    private DepartmentDto toDepartment;
    private String description;
    private Set<ItemDto> items = new HashSet<>();
}
