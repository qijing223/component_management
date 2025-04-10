package com.lot.server.component.service;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.domain.model.ReturnedDTO;

import java.util.List;

public interface ComponentService {
    ComponentDTO createProduct(ComponentDTO dto);
    ComponentDTO updateProduct(Integer id, ComponentDTO dto);
    ComponentDTO getProductById(Integer id);
    List<ComponentDTO> getProductsByStatus(String status);
    void deleteProductById(Integer id);
    List<ComponentDTO> getAllProducts();

    List<ComponentDTO> getBorrowedById(Integer userId);

    List<ReturnedDTO> getReturnedById(Integer id);

    void updateBorrowedByPartId(Integer userId, Integer partId);
    void createReturnedByPartId(Integer userId, Integer partId);
    List<ComponentDTO> getComponentsByProductId(Integer productId);
}

