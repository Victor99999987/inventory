package ru.petproject.inventory.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petproject.inventory.exception.AlreadyExistsException;
import ru.petproject.inventory.exception.NotFoundException;
import ru.petproject.inventory.model.Category;
import ru.petproject.inventory.model.Organization;
import ru.petproject.inventory.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseCategoryService {
    private static final String CATEGORY_NOT_FOUND = "Категория с id %d не найдена";
    private static final String NAME_ALREADY_EXISTS = "Категория %s уже существует";
    private final CategoryRepository categoryRepository;

    public Category getCategory(Organization organization, Long id) {
        return categoryRepository.findByOrganizationAndId(organization, id)
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND, id)));
    }

    public List<Category> getCategories(Organization organization) {
        return categoryRepository.findAllByOrganization(organization);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public void checkExistsCategory(Organization organization, String name) {
        if (categoryRepository.existsByOrganizationAndName(organization, name)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }

    public void checkExistsCategory(Organization organization, String name, Long exceptThisId) {
        if (categoryRepository.existsByOrganizationAndNameAndIdNot(organization, name, exceptThisId)) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_EXISTS, name));
        }
    }
}
