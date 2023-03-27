package no.ntnu.let.letapi.service;

import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.repository.listing.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for categories
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get all categories
     * @return A list of all categories
     */
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Create a new category
     * @param toCategory The category to create
     * @return The created category
     */
    public Category createCategory(Category toCategory) {
        return categoryRepository.save(toCategory);
    }

    /**
     * Update a category
     * @param id The id of the category to update
     * @return The updated category
     */
    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * Update a category
     * @param category The category to update
     */
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
