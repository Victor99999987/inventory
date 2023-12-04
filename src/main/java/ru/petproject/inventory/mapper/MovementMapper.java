package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.model.Movement;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MovementMapper {
    public static MovementDto toDto(Movement movement) {
        Set<ItemDto> items = movement.getItems().stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toSet());
        return MovementDto.builder()
                .id(movement.getId())
                .movementDate(movement.getMovementDate())
                .fromOwner(UserMapper.toDto(movement.getFromOwner()))
                .toOwner(UserMapper.toDto(movement.getToOwner()))
                .fromUser(UserMapper.toDto(movement.getFromUser()))
                .toUser(UserMapper.toDto(movement.getToUser()))
                .fromDepartment(DepartmentMapper.toDto(movement.getFromDepartment()))
                .toDepartment(DepartmentMapper.toDto(movement.getToDepartment()))
                .description(movement.getDescription())
                .items(items)
                .build();
    }
}
