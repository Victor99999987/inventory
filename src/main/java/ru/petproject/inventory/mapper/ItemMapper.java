package ru.petproject.inventory.mapper;

import lombok.experimental.UtilityClass;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.model.Item;

@UtilityClass
public class ItemMapper {
    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .category(CategoryMapper.toDto(item.getCategory()))
                .client(UserMapper.toShortDto(item.getClient()))
                .owner(UserMapper.toShortDto(item.getOwner()))
                .department(DepartmentMapper.toDto(item.getDepartment()))
                .serviceable(item.isServiceable())
                .invNumber(item.getInvNumber())
                .created(item.getCreated())
                .finished(item.getFinished())
                .build();
    }
}
