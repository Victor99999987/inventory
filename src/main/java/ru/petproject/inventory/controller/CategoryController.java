package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.CategoryNewDto;
import ru.petproject.inventory.dto.CategoryUpdateDto;
import ru.petproject.inventory.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final HttpServletRequest request;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto postCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                             @Valid @RequestBody CategoryNewDto categoryNewDto) {
        Utility.logEndpoint(log, request);
        return categoryService.postCategory(userId, categoryNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto patchCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                              @PathVariable @Positive Long id,
                              @Valid @RequestBody CategoryUpdateDto categoryUpdateDto) {
        Utility.logEndpoint(log, request);
        return categoryService.patchCategory(userId, id, categoryUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                        @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        categoryService.deleteCategory(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CategoryDto> getCategories(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        Utility.logEndpoint(log, request);
        return categoryService.getCategories(userId);
    }

}
