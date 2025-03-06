package com.lot.server.category.service;

import com.lot.server.category.domain.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryById(Integer id);
    CategoryDTO getCategoryByName(String name);
    List<CategoryDTO> getAllCategories();
    void addCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategoryById(Integer id);
}

