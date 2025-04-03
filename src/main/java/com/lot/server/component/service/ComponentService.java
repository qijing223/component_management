package com.lot.server.component.service;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.component.domain.model.ComponentDTO;

import java.util.List;

public interface ComponentService {
    ComponentDTO createProduct(ComponentDTO dto);
    ComponentDTO updateProduct(Integer id, ComponentDTO dto);
    ComponentDTO getProductById(Integer id);
    List<ComponentDTO> getProductsByStatus(String status);
    void deleteProductById(Integer id);
    List<ComponentDTO> getAllProducts();
    List<ComponentDTO> getComponentsByProductId(Integer productId);
}

