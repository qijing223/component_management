package com.lot.server.api;

import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.service.PartService;
import com.lot.server.product.domain.model.ProductDTO;
import com.lot.server.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductApi {

    private static final Logger logger = LoggerFactory.getLogger(ProductApi.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private PartService partService;

    // Get category by product ID
    @GetMapping("/{id}")
    public ProductDTO getCategoryById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // Get category by product name
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getCategoryByName(@PathVariable String name) {
        ProductDTO productDTO = productService.getProductByName(name);
        return productDTO != null ? ResponseEntity.ok(productDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get all categories
    @GetMapping
    public List<ProductDTO> getAllCategories() {
        return productService.getAllProducts();
    }

    // Add new category
    @PostMapping
    public ResponseEntity<ProductDTO> addCategory(@RequestBody ProductDTO productDTO) {
        try {
            productService.addProduct(productDTO);
            ProductDTO createdCategory = productService.getProductByName(productDTO.getProductName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    // Update category by product ID
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(id);
        productService.updateProduct(productDTO);
        return "Category updated successfully!";
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            // First check if the product exists
            ProductDTO productDTO = productService.getProductById(id);
            if (productDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found with ID: " + id);
            }
            
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting product with ID {}: {}", id, e.getMessage(), e);
            if (e.getMessage().contains("Cannot delete product") && e.getMessage().contains("currently borrowed out")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cannot delete product: There are parts currently being borrowed.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the product: " + e.getMessage());
        }
    }

    // Get all components by product ID
    @GetMapping("/{id}/part_numbers")
    public ResponseEntity<List<Integer>> getPartNumbersByProductId(@PathVariable Integer id) {
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Integer> partNumbers = productService.getPartNumbersByProductId(id);
        return ResponseEntity.ok(partNumbers);
    }
}
