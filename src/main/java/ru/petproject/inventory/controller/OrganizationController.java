package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.OrganizationDto;
import ru.petproject.inventory.service.OrganizationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
    OrganizationDto patchOrganization(@Valid @RequestBody OrganizationDto organizationDto,
                                      @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт PATCH /organization");
        return null;
    }
}
