package ru.petproject.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.petproject.inventory.common.Const;

import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private CategoryDto category;
    private boolean serviceable;
    private String invNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime finished;
}
