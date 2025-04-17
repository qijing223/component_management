package com.lot.server.product.service.impl;

import com.lot.server.product.domain.model.ProductDTO;
import com.lot.server.product.mapper.ProductMapper;
import com.lot.server.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}