package com.lot.server.category.service.impl;

import com.lot.server.category.domain.entity.CategoryDO;
import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.mapper.CategoryMapper;
import com.lot.server.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        CategoryDO categoryDO = categoryMapper.selectCategoryById(id);
        return categoryDO != null ? convertToDTO(categoryDO) : null;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDO> categoryDOList = categoryMapper.selectAllCategories();
        return categoryDOList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        categoryMapper.insertCategory(convertToDO(categoryDTO));
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        categoryMapper.updateCategory(convertToDO(categoryDTO));
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryMapper.deleteCategoryById(id);
    }

    private CategoryDTO convertToDTO(CategoryDO categoryDO) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(categoryDO.getCategoryId());
        dto.setCategoryName(categoryDO.getCategoryName());
        dto.setAvailableNumber(categoryDO.getAvailableNumber());
        dto.setUsageNumber(categoryDO.getUsageNumber());
        dto.setBorrowNumber(categoryDO.getBorrowNumber());
        return dto;
    }

    private CategoryDO convertToDO(CategoryDTO categoryDTO) {
        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setCategoryId(categoryDTO.getCategoryId());
        categoryDO.setCategoryName(categoryDTO.getCategoryName());
        categoryDO.setAvailableNumber(categoryDTO.getAvailableNumber());
        categoryDO.setUsageNumber(categoryDTO.getUsageNumber());
        categoryDO.setBorrowNumber(categoryDTO.getBorrowNumber());
        return categoryDO;
    }
}
