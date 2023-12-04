package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.ItemDto;
import ru.petproject.inventory.dto.ItemNewDto;
import ru.petproject.inventory.dto.ItemUpdateDto;
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
    ItemDto postItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                     @Valid @RequestBody ItemNewDto itemNewDto) {
        log.info("Получен запрос на эндпоинт POST /items");
        return itemService.postItem(userId, itemNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ItemDto patchItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                      @PathVariable @Positive Long id,
                      @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        log.info("Получен запрос на эндпоинт PATCH /items/{}", id);
        return itemService.patchItem(userId, id, itemUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItem(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                    @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /items/{}", id);
        itemService.deleteItem(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ItemDto> getItems(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /items");
        return null;
    }
}
