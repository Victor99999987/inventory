package ru.petproject.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementNewDto {
    @NotNull(message = "поле fromOwnerId должно быть указано")
    @Positive(message = "поле fromOwnerId должно быть положительным")
    private Long fromOwnerId;

    @NotNull(message = "поле toOwnerId должно быть указано")
    @Positive(message = "поле toOwnerId должно быть положительным")
    private Long toOwnerId;

    @NotNull(message = "поле fromUserId должно быть указано")
    @Positive(message = "поле fromUserId должно быть положительным")
    private Long fromUserId;

    @NotNull(message = "поле toUserId должно быть указано")
    @Positive(message = "поле toUserId должно быть положительным")
    private Long toUserId;

    @NotNull(message = "поле fromDepartmentId должно быть указано")
    @Positive(message = "поле fromDepartmentId должно быть положительным")
    private Long fromDepartmentId;

    @NotNull(message = "поле toDepartmentId должно быть указано")
    @Positive(message = "поле toDepartmentId должно быть положительным")
    private Long toDepartmentId;

    @Size(min = 1, max = 1000, message = "поле description должно содержать от 1 до 1000 символов")
    private String description;

    @NotNull(message = "поле itemsId должно быть указано")
    private Set<Long> itemsId;
}
