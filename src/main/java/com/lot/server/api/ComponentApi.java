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

    // 创建一个新零件组件
    @PostMapping
    public ResponseEntity<ComponentDTO> createComponent(@RequestBody ComponentDTO dto) {
        System.out.println("Received DTO: " + dto);
        ComponentDTO created = componentService.createProduct(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 更新指定 ID 的组件信息
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

    // 获取指定 ID 的组件
    @GetMapping("/{id}")
    public ResponseEntity<ComponentDTO> getComponentById(@PathVariable Integer id) {
        ComponentDTO component = componentService.getProductById(id);
        if (component == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    // get by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComponentDTO>> getComponentsByStatus(@PathVariable String status) {
        List<ComponentDTO> components = componentService.getProductsByStatus(status);
        if (components.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(components, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Integer id) {
        componentService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 获取所有组件
    @GetMapping
    public ResponseEntity<List<ComponentDTO>> getAllComponents() {
        List<ComponentDTO> all = componentService.getAllProducts();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
