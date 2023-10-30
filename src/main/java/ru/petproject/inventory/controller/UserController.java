package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.NewUserDto;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto postUser(@Valid @RequestBody NewUserDto newUserDto,
                     @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт POST /users");
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto patchUser(@Valid @RequestBody UserDto userDto,
                      @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                      @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт PATCH /users/{}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                    @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /users/{}", id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserDto> getUsers(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /users");
        return null;
    }

}
