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

    @PostMapping
    public ResponseEntity<ComponentDTO> createProduct(@RequestBody ComponentDTO dto) {
        ComponentDTO created = componentService.createProduct(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentDTO> updateProduct(@PathVariable Integer id,
                                                      @RequestBody ComponentDTO dto) {
        ComponentDTO updated = componentService.updateProduct(id, dto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentDTO> getProductById(@PathVariable Integer id) {
        ComponentDTO product = componentService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        componentService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ComponentDTO>> getAllProducts() {
        List<ComponentDTO> all = componentService.getAllProducts();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
