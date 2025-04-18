package com.lot.server.product.service.impl;
import java.util.stream.Collectors;


import com.lot.server.product.domain.model.ProductDTO;
import com.lot.server.product.mapper.ProductMapper;
import com.lot.server.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDTO getProductById(Integer id) {
         return productMapper.selectProductById(id);
    }

    @Override
    public ProductDTO getProductByName(String name) {
        return productMapper.selectProductByName(name);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.selectAllProducts();
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        productMapper.insertProduct(productDTO);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        productMapper.updateProduct(productDTO);
    }

    @Override
    public void deleteProductById(Integer id) {
        productMapper.deleteProductById(id);
    }

    @Override
    public List<Integer> getPartNumbersByProductId(Integer productId) {
        ProductDTO product = productMapper.selectProductById(productId);
        if (product == null || product.getPartList() == null) {
            return List.of();
        }

        Object rawParts = product.getPartList().get("parts");
        if (rawParts == null) return List.of();

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<PartInfo> parts = mapper.convertValue(rawParts, new TypeReference<List<PartInfo>>() {});
            return parts.stream()
                    .map(PartInfo::getPart_number)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // 或用 logger.error(...) 打印日志
            return Collections.emptyList();
        }
    }
}