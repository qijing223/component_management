package com.lot.server.category.service.impl;

import com.lot.server.category.domain.entity.CategoryDO;
import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.mapper.CategoryMapper;
import com.lot.server.category.service.CategoryService;
import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ComponentService componentService;

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        CategoryDO categoryDO = categoryMapper.selectCategoryById(id);
        return categoryDO != null ? convertToDTO(categoryDO) : null;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        CategoryDO categoryDO = categoryMapper.selectCategoryByName(name);
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

    @Override
    public List<ComponentDTO> getComponentsByProductId(Integer productId) {
        return componentService.getComponentsByProductId(productId);
    }

    private CategoryDTO convertToDTO(CategoryDO doObj) {
        CategoryDTO dto = new CategoryDTO();
        dto.setProductId(doObj.getProductId());
        dto.setProductName(doObj.getProductName());
        dto.setNumberPartCheckOut(doObj.getNumberPartCheckOut());
        dto.setNumberPartInStock(doObj.getNumberPartInStock());
        dto.setTotalCost(doObj.getTotalCost());
        return dto;
    }

    private CategoryDO convertToDO(CategoryDTO dto) {
        CategoryDO doObj = new CategoryDO();
        doObj.setProductId(dto.getProductId());
        doObj.setProductName(dto.getProductName());
        doObj.setNumberPartCheckOut(dto.getNumberPartCheckOut());
        doObj.setNumberPartInStock(dto.getNumberPartInStock());
        doObj.setTotalCost(dto.getTotalCost());
        return doObj;
    }
}