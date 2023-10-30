package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Const;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.dto.NewDepartmentDto;
import ru.petproject.inventory.service.DepartmentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DepartmentDto postDepartment(@Valid @RequestBody NewDepartmentDto newDepartmentDto,
                                 @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт POST /departments");
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DepartmentDto patchDepartment(@Valid @RequestBody DepartmentDto departmentDto,
                                  @RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                  @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт PATCH /departments/{}",  id);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                         @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /departments/{}",  id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DepartmentDto> getDepartments(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /departments");
        return null;
    }

}
