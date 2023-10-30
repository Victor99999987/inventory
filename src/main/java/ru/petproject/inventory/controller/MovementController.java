package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.NewMovementDto;
import ru.petproject.inventory.service.MovementService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movements")
public class MovementController {
    private final MovementService movementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MovementDto postMovement(@Valid @RequestBody NewMovementDto newMovementDto,
                             @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт POST /movements");
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MovementDto patchMovement(@Valid @RequestBody MovementDto movementDto,
                              @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                              @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт PATCH /movements/{}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMovement(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                        @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /movements/{}", id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MovementDto> getCategories(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /movements");
        return null;
    }
    
}
