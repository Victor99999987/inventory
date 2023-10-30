package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.RegistrationDto;
import ru.petproject.inventory.service.RegistrationService;

import javax.validation.Valid;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    RegistrationDto postRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        log.info("Получен запрос на эндпоинт POST /registration");
        return null;
    }

}
