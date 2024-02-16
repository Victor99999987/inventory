package ru.petproject.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.dto.DepartmentNewDto;
import ru.petproject.inventory.dto.DepartmentUpdateDto;
import ru.petproject.inventory.service.DepartmentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.petproject.inventory.common.Const.REQUEST_HEADER_USER_ID;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final HttpServletRequest request;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DepartmentDto postDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                 @Valid @RequestBody DepartmentNewDto departmentNewDto) {
        Utility.logEndpoint(log, request);
        return departmentService.postDepartment(userId, departmentNewDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DepartmentDto patchDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                  @PathVariable @Positive Long id,
                                  @Valid @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        Utility.logEndpoint(log, request);
        return departmentService.patchDepartment(userId, id, departmentUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                          @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        departmentService.deleteDepartment(userId, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DepartmentDto> getDepartments(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId) {
        Utility.logEndpoint(log, request);
        return departmentService.getDepartments(userId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DepartmentDto getDepartment(@RequestHeader(REQUEST_HEADER_USER_ID) @Positive Long userId,
                                @PathVariable @Positive Long id) {
        Utility.logEndpoint(log, request);
        return departmentService.getDepartment(userId, id);
    }

}
