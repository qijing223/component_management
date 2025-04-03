package com.lot.server.api;

import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.service.CategoryService;
import com.lot.server.component.domain.model.ComponentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {

    private static final Logger logger = LoggerFactory.getLogger(CategoryApi.class);

    @Autowired
    private CategoryService categoryService;

    // Get category by product ID
    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    // Get category by product name
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get all categories
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Add new category
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.addCategory(categoryDTO);
            CategoryDTO createdCategory = categoryService.getCategoryByName(categoryDTO.getProductName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    // Update category by product ID
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setProductId(id);
        categoryService.updateCategory(categoryDTO);
        return "Category updated successfully!";
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            // First check if the product exists
            CategoryDTO category = categoryService.getCategoryById(id);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found with ID: " + id);
            }
            
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok("Product deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting product with ID {}: {}", id, e.getMessage(), e);
            if (e.getMessage().contains("Cannot delete product") && e.getMessage().contains("currently borrowed out")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cannot delete product: There are parts currently being borrowed.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the product: " + e.getMessage());
        }
    }

    // Get all components by product ID
    @GetMapping("/{id}/parts")
    public ResponseEntity<List<ComponentDTO>> getPartsByProductId(@PathVariable Integer id) {
        // First check if the product exists
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        
        List<ComponentDTO> components = categoryService.getComponentsByProductId(id);
        return ResponseEntity.ok(components);
    }
}
