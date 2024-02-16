package ru.petproject.inventory.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.model.Department;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseDepartmentService {
    private static final String DEPARTMENT_NOT_FOUND = "Подразделение с id %d не найдено";
    private static final String NAME_ALREADY_EXISTS = "Подразделение %s уже существует";
    private final DepartmentRepository departmentRepository;

    public Department getDepartment(Organization organization, Long id) {
        return departmentRepository.findByOrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(DEPARTMENT_NOT_FOUND, id)));
    }

    public List<Department> getDepartments(Organization organization) {
        return departmentRepository.findAllByOrganization(organization);
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    public void checkExistsDepartment(Organization organization, String name) {
        if (departmentRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }

    public void checkExistsDepartment(Organization organization, String name, Long exceptThisId) {
        if (departmentRepository.existsByOrganizationAndNameAndIdNot(organization, name, exceptThisId)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }
}
