package com.lot.server.component.service.impl;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.domain.entity.ComponentDO;
import com.lot.server.component.mapper.ComponentMapper;
import com.lot.server.component.service.ComponentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentMapper componentMapper;

    @Override
    public ComponentDTO createProduct(ComponentDTO dto) {
        ComponentDO entity = convertToDO(dto);
        componentMapper.insertProduct(entity);
        // entity.productsId is now populated with generated key
        return convertToDTO(entity);
    }

    @Override
    public ComponentDTO updateProduct(Integer id, ComponentDTO dto) {
        // First fetch the existing record
        ComponentDO existing = componentMapper.selectProductById(id);
        if (existing == null) {
            // In real scenario, you might throw an exception
            return null;
        }
        existing.setProductName(dto.getProductName());
        existing.setCategory(dto.getCategory());
        componentMapper.updateProduct(existing);
        return convertToDTO(existing);
    }

    @Override
    public ComponentDTO getProductById(Integer id) {
        ComponentDO product = componentMapper.selectProductById(id);
        if (product == null) {
            return null;
            // or throw an exception if preferred
        }
        return convertToDTO(product);
    }

    @Override
    public void deleteProductById(Integer id) {
        componentMapper.deleteProductById(id);
    }

    @Override
    public List<ComponentDTO> getAllProducts() {
        return componentMapper.selectAllProducts()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ComponentDO convertToDO(ComponentDTO dto) {
        ComponentDO entity = new ComponentDO();
        entity.setProductsId(dto.getProductsId());
        entity.setProductName(dto.getProductName());
        entity.setCategory(dto.getCategory());
        return entity;
    }

    private ComponentDTO convertToDTO(ComponentDO entity) {
        if (entity == null) {
            return null;
        }
        ComponentDTO dto = new ComponentDTO();
        dto.setProductsId(entity.getProductsId());
        dto.setProductName(entity.getProductName());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}

