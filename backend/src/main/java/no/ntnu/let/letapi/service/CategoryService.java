package no.ntnu.let.letapi.service;

import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.repository.listing.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category toCategory) {
        return categoryRepository.save(toCategory);
    }

    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
