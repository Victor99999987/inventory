package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.dto.DepartmentNewDto;
import ru.petproject.inventory.dto.DepartmentUpdateDto;
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
    DepartmentDto postDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                 @Valid @RequestBody DepartmentNewDto departmentNewDto) {
        log.info("Получен запрос на эндпоинт POST /departments");
        return departmentService.postDepartment(userId, departmentNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DepartmentDto patchDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                  @PathVariable @Positive Long id,
                                  @Valid @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        log.info("Получен запрос на эндпоинт PATCH /departments/{}", id);
        return departmentService.patchDepartment(userId, id, departmentUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                         @PathVariable @Positive Long id) {
        log.info("Получен запрос на эндпоинт DELETE /departments/{}", id);
        departmentService.deleteDepartment(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DepartmentDto> getDepartments(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        log.info("Получен запрос на эндпоинт GET /departments");
        return departmentService.getDepartments(userId);
    }
}
