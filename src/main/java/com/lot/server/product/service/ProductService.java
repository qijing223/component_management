package com.lot.server.product.service;

import com.lot.server.product.domain.model.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Integer id);
    ProductDTO getProductByName(String name);
    List<ProductDTO> getAllProducts();
    void addProduct(ProductDTO categoryDTO);
    void updateProduct(ProductDTO categoryDTO);
    void deleteProductById(Integer id);
    public List<Integer> getPartNumbersByProductId(Integer productId);
}

