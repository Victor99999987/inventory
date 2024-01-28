package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.ItemNewDto;
import ru.petproject.inventory.dto.ItemUpdateDto;
import ru.petproject.inventory.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final HttpServletRequest request;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ItemDto postItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                     @Valid @RequestBody ItemNewDto itemNewDto) {
        Utility.logEndpoint(log, request);
        return itemService.postItem(userId, itemNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ItemDto patchItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                      @PathVariable @Positive Long id,
                      @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        Utility.logEndpoint(log, request);
        return itemService.patchItem(userId, id, itemUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                    @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        itemService.deleteItem(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ItemDto> getItems(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        Utility.logEndpoint(log, request);
        return null;
    }
}
