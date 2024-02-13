package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.dto.CategoryDto;
import ru.petproject.inventory.dto.DepartmentDto;
import ru.petproject.inventory.dto.DepartmentNewDto;
import ru.petproject.inventory.dto.DepartmentUpdateDto;
import ru.petproject.inventory.exception.AccessDeniedException;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.ExistsRelatedException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.mapper.CategoryMapper;
import ru.petproject.inventory.mapper.DepartmentMapper;
import ru.petproject.inventory.model.*;
import ru.petproject.inventory.repository.DepartmentRepository;
import ru.petproject.inventory.repository.ItemRepository;
import ru.petproject.inventory.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public DepartmentDto postDepartment(Long userId, DepartmentNewDto departmentNewDto) {
        User user = getUser(userId);
        checkAccess(user);
        checkExistsDepartment(user.getOrganization(), departmentNewDto.getName());
        Department department = Department.builder()
                .name(departmentNewDto.getName())
                .address(departmentNewDto.getAddress())
                .organization(user.getOrganization())
                .build();
        department = departmentRepository.save(department);
        return DepartmentMapper.toDto(department);
    }

    @Transactional
    public DepartmentDto patchDepartment(Long userId, Long id, DepartmentUpdateDto departmentUpdateDto) {
        User user = getUser(userId);
        checkAccess(user);
        Department department = getDepartment(user.getOrganization(), id);
        if (departmentUpdateDto.getName() != null) {
            checkBlank("name", departmentUpdateDto.getName());
            checkExistsDepartment(user.getOrganization(), departmentUpdateDto.getName(), id);
            department.setName(departmentUpdateDto.getName());
        }
        if (departmentUpdateDto.getAddress() != null) {
            checkBlank("address", departmentUpdateDto.getAddress());
            department.setAddress(departmentUpdateDto.getAddress());
        }
        department = departmentRepository.save(department);
        return DepartmentMapper.toDto(department);
    }

    @Transactional
    public void deleteDepartment(Long userId, Long id) {
        User user = getUser(userId);
        checkAccess(user);
        Department department = getDepartment(user.getOrganization(), id);
        checkExistItem(department);
        departmentRepository.delete(department);
    }

    @Transactional
    public List<DepartmentDto> getDepartments(Long userId) {
        User user = getUser(userId);
        return departmentRepository.findAllByOrganization(user.getOrganization()).stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDto getDepartment(Long userId, Long id) {
        User user = getUser(userId);
        Department department = getDepartment(user.getOrganization(), id);
        return DepartmentMapper.toDto(department);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    private void checkAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(String.format("Пользователь с id %d не является администратором", user.getId()));
        }
    }

    private Department getDepartment(Organization organization, Long departmentId) {
        return departmentRepository.findByOrganizationAndId(organization, departmentId)
                .orElseThrow(() -> new NotFoundException(String.format("Подразделение с id %d не найдено", departmentId)));
    }

    private void checkExistsDepartment(Organization organization, String name) {
        if (departmentRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format("Подразделение с name %s уже существует", name));
        }
    }

    private void checkExistsDepartment(Organization organization, String name, Long notThisId) {
        if (departmentRepository.existsByOrganizationAndNameAndIdNot(organization, name, notThisId)) {
            throw new AlreadyExistsException(String.format("Подразделение c name %s уже существует", name));
        }
    }

    private void checkBlank(String name, String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле " + name + " не может состоять из пробелов");
        }
    }

    private void checkExistItem(Department department) {
        if (itemRepository.existsByDepartment(department)) {
            throw new ExistsRelatedException(String.format("В Подразделении %s есть оборудование", department.getName()));
        }
    }

}
