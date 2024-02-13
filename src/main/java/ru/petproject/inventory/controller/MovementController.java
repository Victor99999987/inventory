package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.MovementNewDto;
import ru.petproject.inventory.service.MovementService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.petproject.inventory.common.Const.PATTERN_DATE;
import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {
    private final MovementService movementService;
    private final HttpServletRequest request;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MovementDto postMovement(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                             @Valid @RequestBody MovementNewDto movementNewDto) {
        Utility.logEndpoint(log, request);
        return movementService.postMovement(userId, movementNewDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MovementDto> getMovements(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                   @RequestParam(required = false) @Positive Long fromOwnerId,
                                   @RequestParam(required = false) @Positive Long toOwnerId,
                                   @RequestParam(required = false) @Positive Long fromClientId,
                                   @RequestParam(required = false) @Positive Long toClientId,
                                   @RequestParam(required = false) @Positive Long fromDepartmentId,
                                   @RequestParam(required = false) @Positive Long toDepartmentId,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = PATTERN_DATE) LocalDateTime fromDate,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = PATTERN_DATE) LocalDateTime toDate,
                                   @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                   @RequestParam(defaultValue = "100") @Positive int size) {
        Utility.logEndpoint(log, request);
        return movementService.getMovements(userId, fromOwnerId, toOwnerId, fromClientId, toClientId,
                fromDepartmentId, toDepartmentId, fromDate, toDate, from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MovementDto getMovement(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                            @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        return movementService.getMovement(userId, id);
    }

}
