package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Const;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.NewCategoryDto;
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
    CategoryDto postCategory(@Valid @RequestBody NewCategoryDto newCategoryDto,
                             @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт POST /categories");
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto patchCategory(@Valid @RequestBody CategoryDto categoryDto,
                              @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                              @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт PATCH /categories/{}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                       @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /categories/{}", id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CategoryDto> getCategories(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /categories");
        return null;
    }

}
