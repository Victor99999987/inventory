package ru.petproject.inventory.common;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class Utility {
    public static void logEndpoint(Logger log, HttpServletRequest request) {
        log.info("Получен запрос {} {}", request.getMethod(), request.getRequestURI());
    }

    public static void checkBlank(String fieldName, String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле " + fieldName + " не может состоять из пробелов");
        }
    }

    public void checkFinishedIsBeforeCreated(LocalDateTime finished, LocalDateTime created) {
        if (finished.isBefore(created)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше начала");
        }
    }

    public static void checkNotNullIds(Set<Long> ids) {
        if (ids.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Id должно быть указано");
        }
    }

    public static void checkPositiveIds(Set<Long> ids) {
        if (ids.stream().anyMatch(i -> i <= 0)) {
            throw new IllegalArgumentException("Id должно быть положительным");
        }
    }
}
