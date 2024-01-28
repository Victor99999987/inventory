package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.RegistrationDto;
import ru.petproject.inventory.dto.RegistrationNewDto;
import ru.petproject.inventory.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final HttpServletRequest request;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    RegistrationDto postRegistration(@Valid @RequestBody RegistrationNewDto registrationNewDto) {
        Utility.logEndpoint(log, request);
        return registrationService.postRegistration(registrationNewDto);
    }

    @PatchMapping("/activate/{UserId}")
    @ResponseStatus(HttpStatus.OK)
    RegistrationDto patchActivate(@PathVariable @Positive Long userId,
                                  @RequestParam @NotNull String code) {
        Utility.logEndpoint(log, request);
        return registrationService.patchActivate(userId, code);
    }
}
