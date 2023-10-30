package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.NewItemDto;
import ru.petproject.inventory.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ItemDto postItem(@Valid @RequestBody NewItemDto newItemDto,
                     @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт POST /items");
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ItemDto patchItem(@Valid @RequestBody ItemDto itemDto,
                      @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                      @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт PATCH /items/{}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                    @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /items/{}", id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ItemDto> getCategories(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /items");
        return null;
    }
}
