package no.ntnu.let.letapi.controller.listing;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.listing.CategoryCreationDTO;
import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.security.AuthenticationService;
import no.ntnu.let.letapi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Category controller
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final ListingMapper listingMapper;
    private final AuthenticationService authenticationService;
    /**
     * Get all categories
     *
     * @return List of categories
     */
    @GetMapping
    public ResponseEntity<Object> getCategories() {
        var categories = categoryService.getCategories();
        return ResponseEntity.ok(categories.stream().map(listingMapper::toCategoryDTO).toList());
    }

    /**
     * Create a new category
     *
     * @param categoryDTO Category creation DTO
     * @return The crated category
     */
    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryCreationDTO categoryDTO) {
        Boolean isAdmin = authenticationService.isAdmin();
        if (isAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        logger.info("Creating category with name " + categoryDTO.getName());
        Category category = categoryService.createCategory(listingMapper.toCategory(categoryDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(listingMapper.toCategoryDTO(category));
    }

    /**
     * Get a category by id
     *
     * @param id Category id
     * @return The category
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable long id) {
        Category category = categoryService.getCategory(id);
        if (category == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listingMapper.toCategoryDTO(category));
    }

    /**
     * Update a category
     *
     * @param id Category id
     * @return The updated category
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        Boolean isAdmin = authenticationService.isAdmin();
        if (isAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        logger.info("Deleting category with id " + id);
        Category category = categoryService.getCategory(id);
        if (category == null) return ResponseEntity.notFound().build();

        try {
            categoryService.deleteCategory(category);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Failed to delete category with id " + id + " due to foreign key constraint");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category is in use");
        }
        return ResponseEntity.ok(listingMapper.toCategoryDTO(category));
    }
}
