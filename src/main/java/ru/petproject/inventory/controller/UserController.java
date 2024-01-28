package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.dto.UserNewDto;
import ru.petproject.inventory.dto.UserUpdateDto;
import ru.petproject.inventory.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final HttpServletRequest request;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto postUser(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                     @Valid @RequestBody UserNewDto userNewDto) {
        Utility.logEndpoint(log, request);
        return userService.postUser(userId, userNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto patchUser(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                      @PathVariable @Positive Long id,
                      @Valid @RequestBody UserUpdateDto userUpdateDto) {
        Utility.logEndpoint(log, request);
        return userService.patchUser(userId, id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                    @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        userService.deleteUser(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserDto> getUsers(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        Utility.logEndpoint(log, request);
        return userService.getUsers(userId);
    }
}
