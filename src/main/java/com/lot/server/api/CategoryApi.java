package com.lot.server.api;

import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public String addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return "Category added successfully!";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setCategoryId(id);
        categoryService.updateCategory(categoryDTO);
        return "Category updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
        return "Category deleted successfully!";
    }
}
