package ru.petproject.inventory.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime timestamp;
}

