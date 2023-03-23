package no.ntnu.let.letapi.controller.listing;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.listing.CategoryCreationDTO;
import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.security.UserAuthentication;
import no.ntnu.let.letapi.security.UserDetailsImpl;
import no.ntnu.let.letapi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final ListingMapper listingMapper;

    @GetMapping
    public ResponseEntity<Object> getCategories() {
        var categories = categoryService.getCategories();
        return ResponseEntity.ok(categories.stream().map(listingMapper::toCategoryDTO).toList());
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryCreationDTO categoryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!((UserDetailsImpl) authentication.getPrincipal()).isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Category category = categoryService.createCategory(listingMapper.toCategory(categoryDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(listingMapper.toCategoryDTO(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable long id) {
        Category category = categoryService.getCategory(id);
        if (category == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listingMapper.toCategoryDTO(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!((UserDetailsImpl) authentication.getPrincipal()).isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Category category = categoryService.getCategory(id);
        if (category == null) return ResponseEntity.notFound().build();

        categoryService.deleteCategory(category);
        return ResponseEntity.ok(listingMapper.toCategoryDTO(category));
    }
}
