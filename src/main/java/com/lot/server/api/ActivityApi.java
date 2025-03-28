package com.lot.server.api;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.service.ActivityService;
import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.service.CategoryService;
import com.lot.server.component.domain.entity.ComponentStatus;
import com.lot.server.component.domain.model.ComponentDTO;
import com.lot.server.component.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityApi {
    private final ActivityService activityService;
    private final CategoryService categoryService;
    private final ComponentService componentService; // Assuming product is represented by ComponentDTO

    @Autowired
    public ActivityApi(ActivityService activityService,
                       CategoryService categoryService,
                       ComponentService componentService) {
        this.activityService = activityService;
        this.categoryService = categoryService;
        this.componentService = componentService;
    }

    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(@RequestBody Map<String, Object> requestData) {
        System.out.println("Received request data: " + requestData);
        Integer partId = (Integer) requestData.get("part_id");
        Integer productId = (Integer) requestData.get("product_id");
        Integer employeeId = (Integer) requestData.get("employee_id");
        String partName = (String) requestData.get("part_name");
        Object costObj = requestData.get("cost");
        Double cost = null;
        if (costObj instanceof Integer) {
            cost = ((Integer) costObj).doubleValue();
        } else if (costObj instanceof Double) {
            cost = (Double) costObj;
        }

        System.out.println("Received request data: " +partId+","+productId+","+employeeId+","+cost+","+partName);

        if (partId == null || employeeId == null || productId == null || cost == null || partName == null) {
            return ResponseEntity.badRequest().body("part_id, product_id, employee_id, cost, part_name are required.");
        }

        CategoryDTO categoryDTO = categoryService.getCategoryById(productId);
        if (categoryDTO == null) {
            return ResponseEntity.badRequest().body("Product not found for product id, please create product first: " + productId);
        }

        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setPartId(partId);
        componentDTO.setProductId(productId);
        componentDTO.setStatus(ComponentStatus.AVAILABLE);
        componentDTO.setCost(cost);
        componentDTO.setProductName(categoryDTO.getProductName());
        componentDTO.setPartName(partName);

        try {
            componentService.createProduct(componentDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create part: " + e.getMessage());
        }

        categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() + 1);
        categoryDTO.setTotalCost(categoryDTO.getTotalCost() + cost);
        categoryService.updateCategory(categoryDTO);
        // Step 3: Create an activity record for stock-in
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setPartId(partId);
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(employeeId);
        activityDTO.setAction(ActivityAction.STOCK_IN);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Save activity record
        activityService.stockIn(activityDTO);

        return ResponseEntity.ok("Stock-in operation successful.");
    }


    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody Map<String, Object> requestData) {
        Integer partId = (Integer) requestData.get("part_id");
        Integer employeeId = (Integer) requestData.get("employee_id");

        if (partId == null || employeeId == null) {
            return ResponseEntity.badRequest().body("part_id and employee_id are required.");
        }

        // Step 1: Retrieve product details
        ComponentDTO componentDTO = componentService.getProductById(partId);
        if (componentDTO == null) {
            return ResponseEntity.badRequest().body("Product not found.");
        }

        // Step 2: Check if the product is available for borrowing
        if (componentDTO.getStatus() != ComponentStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body("Product is not available for borrowing.");
        }

        // Step 3: Retrieve category details using category ID
        Integer productId = componentDTO.getProductId();
        if (productId != null) {
            CategoryDTO categoryDTO = categoryService.getCategoryById(productId);
            if (categoryDTO != null) {
                categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() - 1);
                categoryDTO.setNumberPartCheckOut(categoryDTO.getNumberPartCheckOut() + 1);
                categoryService.updateCategory(categoryDTO);
            }
        }

        // Step 5: Update product status to "Borrowed"
        componentDTO.setStatus(ComponentStatus.BORROW_OUT);
        componentService.updateProduct(partId, componentDTO);

        // Step 6: Create an activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setPartId(partId);
        activityDTO.setEmployeeId(employeeId);
        activityDTO.setProductId(productId);
        activityDTO.setAction(ActivityAction.BORROW);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.borrow(activityDTO);

        return ResponseEntity.ok("Borrow operation successful.");
    }



    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody Map<String, Object> requestData) {
        Integer partId = (Integer) requestData.get("part_id");
        Integer employeeId = (Integer) requestData.get("employee_id");

        if (partId == null || employeeId == null) {
            return ResponseEntity.badRequest().body("Product ID and User ID are required.");
        }

        // Step 1: Retrieve product details
        ComponentDTO componentDTO = componentService.getProductById(partId);
        if (componentDTO == null) {
            return ResponseEntity.badRequest().body("Product not found.");
        }

        // Step 2: Check if the product is actually borrowed
        if (componentDTO.getStatus() != ComponentStatus.BORROW_OUT) {
            return ResponseEntity.badRequest().body("Product is not currently borrowed.");
        }

        // Step 3: Retrieve category details using category ID
        Integer productId = componentDTO.getProductId();
        if (productId != null) {
            CategoryDTO categoryDTO = categoryService.getCategoryById(productId);
            if (categoryDTO != null) {
                // Step 4: Increase category available count
                categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() + 1);
                categoryDTO.setNumberPartCheckOut(categoryDTO.getNumberPartCheckOut() - 1);
                categoryService.updateCategory(categoryDTO);
            }
        }


        // Step 5: Update product status back to "Available"
        componentDTO.setStatus(ComponentStatus.AVAILABLE);
        componentService.updateProduct(partId, componentDTO);

        // Step 6: Create an activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setPartId(partId);
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(employeeId);
        activityDTO.setAction(ActivityAction.RETURN);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.returnItem(activityDTO);

        return ResponseEntity.ok("Return operation successful.");
    }


}
