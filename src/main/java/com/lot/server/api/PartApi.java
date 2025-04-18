package com.lot.server.api;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.common.context.UserContext;
import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.domain.model.ReturnedDTO;
import com.lot.server.part.service.PartService;
import com.lot.server.product.domain.model.ProductDTO;
import com.lot.server.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class PartApi {

    @Autowired
    private PartService partService;
    
    @Autowired
    private ProductService productService;

    // Create a new component
    @PostMapping
    public ResponseEntity<PartDTO> createComponent(@RequestBody PartDTO dto) {
        System.out.println("Received DTO: " + dto);
        PartDTO created = partService.createPart(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Update component information by ID
    @PutMapping("/{id}")
    public ResponseEntity<PartDTO> updateComponent(@PathVariable Integer id,
                                                        @RequestBody PartDTO dto) {
        System.out.println("Received DTO: " + dto);
        PartDTO updated = partService.updatePart(id, dto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Get component by ID
    @GetMapping("/{id}")
    public ResponseEntity<PartDTO> getComponentById(@PathVariable Integer id) {
        PartDTO component = partService.getPartById(id);
        if (component == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    // Get components by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PartDTO>> getComponentsByStatus(@PathVariable String status) {
        System.out.println("Received status: " + status);
        List<PartDTO> components = partService.getPartsByStatus(status);
        System.out.println(components);

        if (components.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(components, HttpStatus.OK);
    }

    // Delete component
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Integer id) {
        partService.deletePartById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all components
    @GetMapping
    public ResponseEntity<List<PartDTO>> getAllComponents() {
        List<PartDTO> all = partService.getAllParts();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/borrowed")
    public ResultTO<List<PartDTO>> getBorrowedComponents(){
        Integer userId = UserContext.getUserId();
        List<PartDTO> borrowedComponent = partService.getBorrowedById(userId);
        return ResultTO.success(borrowedComponent);
    }

    @GetMapping("/returned")
    public ResultTO<List<ReturnedDTO>> getReturnedComponents(){
        Integer userId = UserContext.getUserId();
        List<ReturnedDTO> returnedDTO = partService.getReturnedById(userId);
        return ResultTO.success(returnedDTO);
    }
    
    // Get product information by part ID
    @GetMapping("/{id}/product")
    public ResponseEntity<ProductDTO> getProductByPartId(@PathVariable Integer id) {
        PartDTO part = partService.getPartById(id);
        if (part == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Integer productId = part.getProductId();
        if (productId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ProductDTO product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
