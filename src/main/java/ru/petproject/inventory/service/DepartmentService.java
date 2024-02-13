package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.dto.DepartmentNewDto;
import ru.petproject.inventory.dto.DepartmentUpdateDto;
import ru.petproject.inventory.mapper.DepartmentMapper;
import ru.petproject.inventory.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final BaseUserService baseUserService;
    private final BaseDepartmentService baseDepartmentService;
    private final BaseItemService baseItemService;

    @Transactional
    public DepartmentDto postDepartment(Long userId, DepartmentNewDto departmentNewDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        baseDepartmentService.checkExistsDepartment(user.getOrganization(), departmentNewDto.getName());
        Department department = Department.builder()
                .name(departmentNewDto.getName())
                .address(departmentNewDto.getAddress())
                .organization(user.getOrganization())
                .build();
        department = baseDepartmentService.saveDepartment(department);
        return DepartmentMapper.toDto(department);
    }

    @Transactional
    public DepartmentDto patchDepartment(Long userId, Long id, DepartmentUpdateDto departmentUpdateDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Department department = baseDepartmentService.getDepartment(user.getOrganization(), id);
        if (departmentUpdateDto.getName() != null) {
            Utility.checkBlank("name", departmentUpdateDto.getName());
            baseDepartmentService.checkExistsDepartment(user.getOrganization(), departmentUpdateDto.getName(), id);
            department.setName(departmentUpdateDto.getName());
        }
        if (departmentUpdateDto.getAddress() != null) {
            Utility.checkBlank("address", departmentUpdateDto.getAddress());
            department.setAddress(departmentUpdateDto.getAddress());
        }
        department = baseDepartmentService.saveDepartment(department);
        return DepartmentMapper.toDto(department);
    }

    @Transactional
    public void deleteDepartment(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        Department department = baseDepartmentService.getDepartment(user.getOrganization(), id);
        baseItemService.checkExistsItem(department);
        baseDepartmentService.deleteDepartment(department);
    }

    @Transactional
    public List<DepartmentDto> getDepartments(Long userId) {
        User user = baseUserService.getUser(userId);
        return baseDepartmentService.getDepartments(user.getOrganization()).stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDto getDepartment(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        Department department = baseDepartmentService.getDepartment(user.getOrganization(), id);
        return DepartmentMapper.toDto(department);
    }
}
