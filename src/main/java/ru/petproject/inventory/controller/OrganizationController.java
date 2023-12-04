package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.MovementDto;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.dto.OrganizationUpdateDto;
import ru.petproject.inventory.service.OrganizationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    OrganizationDto patchOrganization(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                      @Valid @RequestBody OrganizationUpdateDto organizationUpdateDto) {
        log.info("Получен запрос на эндпоинт PATCH /organization");
        return organizationService.patchOrganization(userId, organizationUpdateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    OrganizationDto getOrganization(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /organization");
        return organizationService.getOrganization(userId);
    }
}
