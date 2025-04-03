package com.lot.server.api;

import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentApi {

    @Autowired
    private ComponentService componentService;

    // Create a new component
    @PostMapping
    public ResponseEntity<ComponentDTO> createComponent(@RequestBody ComponentDTO dto) {
        System.out.println("Received DTO: " + dto);
        ComponentDTO created = componentService.createProduct(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Update component information by ID
    @PutMapping("/{id}")
    public ResponseEntity<ComponentDTO> updateComponent(@PathVariable Integer id,
                                                        @RequestBody ComponentDTO dto) {
        System.out.println("Received DTO: " + dto);
        ComponentDTO updated = componentService.updateProduct(id, dto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Get component by ID
    @GetMapping("/{id}")
    public ResponseEntity<ComponentDTO> getComponentById(@PathVariable Integer id) {
        ComponentDTO component = componentService.getProductById(id);
        if (component == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    // Get components by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComponentDTO>> getComponentsByStatus(@PathVariable String status) {
        List<ComponentDTO> components = componentService.getProductsByStatus(status);
        if (components.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(components, HttpStatus.OK);
    }

    // Delete component
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Integer id) {
        componentService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all components
    @GetMapping
    public ResponseEntity<List<ComponentDTO>> getAllComponents() {
        List<ComponentDTO> all = componentService.getAllProducts();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
