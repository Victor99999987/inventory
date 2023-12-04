package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.CategoryNewDto;
import ru.petproject.inventory.dto.CategoryUpdateDto;
import ru.petproject.inventory.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto postCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                             @Valid @RequestBody CategoryNewDto categoryNewDto) {
        log.info("Получен запрос на эндпоинт POST /categories");
        return categoryService.postCategory(userId, categoryNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto patchCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                              @PathVariable @Positive Long id,
                              @Valid @RequestBody CategoryUpdateDto categoryUpdateDto) {
        log.info("Получен запрос на эндпоинт PATCH /categories/{}", id);
        return categoryService.patchCategory(userId, id, categoryUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                        @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /categories/{}", id);
        categoryService.deleteCategory(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CategoryDto> getCategories(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /categories");
        return categoryService.getCategories(userId);
    }
}
