package com.lot.server.api;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.service.ActivityService;
import com.lot.server.category.domain.model.CategoryDTO;
import com.lot.server.category.service.CategoryService;
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
        Integer productId = (Integer) requestData.get("productId");
        String categoryName = (String) requestData.get("categoryName");
        String productName = (String) requestData.get("productName");
        Integer userId = (Integer) requestData.get("userId");

        if (productId == null || userId == null) {
            return ResponseEntity.badRequest().body("Product ID and user ID are required.");
        }

        // Step 1: Create a new product (ComponentDTO) under this category
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setProductsId(productId);
        if (productName != null) {
            componentDTO.setProductName(productName);
        }

        // Step 2: Check if the category exists, otherwise create a new one
        if (categoryName != null) {
            // TODO: implement getCategoryByName API
            CategoryDTO categoryDTO = categoryService.getCategoryByName(categoryName);
            if (categoryDTO == null) {
                // Create a new category if it doesn't exist
                categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryName(categoryName);
                categoryDTO.setAvailableNumber(1);
                categoryService.addCategory(categoryDTO);
            } else {
                // Increment available number
                categoryDTO.setAvailableNumber(categoryDTO.getAvailableNumber() + 1);
                categoryService.updateCategory(categoryDTO);
            }
            // TODO: componentDTO's setCategory method should link category ID not string
            componentDTO.setCategory(categoryDTO.getCategoryId());
        }

        componentService.createProduct(componentDTO); // Assuming this method creates and assigns an ID

        // Step 3: Create an activity record for stock-in
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(userId);
        activityDTO.setAction(ActivityAction.STOCK_IN);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Save activity record
        activityService.stockIn(activityDTO);

        return ResponseEntity.ok("Stock-in operation successful.");
    }


    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody Map<String, Object> requestData) {
        Integer productId = (Integer) requestData.get("productId");
        Integer userId = (Integer) requestData.get("userId");

        if (productId == null || userId == null) {
            return ResponseEntity.badRequest().body("Product ID and User ID are required.");
        }

        // Step 1: Retrieve product details
        ComponentDTO componentDTO = componentService.getProductById(productId);
        if (componentDTO == null) {
            return ResponseEntity.badRequest().body("Product not found.");
        }

        // Step 2: Check if the product is available for borrowing
        // TODO: Implement getStatus
        if (!"Available".equals(componentDTO.getStatus())) {
            return ResponseEntity.badRequest().body("Product is not available for borrowing.");
        }

        // Step 3: Retrieve category details using category ID
        // TODO: componentDTO.getCategory() return integer ID
        Integer categoryId = componentDTO.getCategory();
        if (categoryId != null) {
            CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
            if (categoryDTO != null) {
                if (categoryDTO.getAvailableNumber() > 0) {
                    categoryDTO.setAvailableNumber(categoryDTO.getAvailableNumber() - 1);
                    categoryService.updateCategory(categoryDTO);
                } else {
                    return ResponseEntity.badRequest().body("No available items in this category.");
                }
            }
        }

        // Step 5: Update product status to "Borrowed"
        // TODO: Implement setStatus
        componentDTO.setStatus("Borrowed");
        componentService.updateProduct(productId, componentDTO);

        // Step 6: Create an activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(userId);
        activityDTO.setAction(ActivityAction.BORROW);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.borrow(activityDTO);

        return ResponseEntity.ok("Borrow operation successful.");
    }



    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody Map<String, Object> requestData) {
        Integer productId = (Integer) requestData.get("productId");
        Integer userId = (Integer) requestData.get("userId");

        if (productId == null || userId == null) {
            return ResponseEntity.badRequest().body("Product ID and User ID are required.");
        }

        // Step 1: Retrieve product details
        ComponentDTO componentDTO = componentService.getProductById(productId);
        if (componentDTO == null) {
            return ResponseEntity.badRequest().body("Product not found.");
        }

        // Step 2: Check if the product is actually borrowed
        // TODO: Implement getStatus
        if (!"Borrowed".equals(componentDTO.getStatus())) {
            return ResponseEntity.badRequest().body("Product is not currently borrowed.");
        }

        // Step 3: Retrieve category details using category ID
        // TODO: componentDTO.getCategory() return integer ID
        Integer categoryId = componentDTO.getCategory();
        if (categoryId != null) {
            CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
            if (categoryDTO != null) {
                // Step 4: Increase category available count
                categoryDTO.setAvailableNumber(categoryDTO.getAvailableNumber() + 1);
                categoryService.updateCategory(categoryDTO);
            }
        }


        // Step 5: Update product status back to "Available"
        // TODO: Implement setStatus
        componentDTO.setStatus("Available");
        componentService.updateProduct(productId, componentDTO);

        // Step 6: Create an activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(userId);
        activityDTO.setAction(ActivityAction.RETURN);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.returnItem(activityDTO);

        return ResponseEntity.ok("Return operation successful.");
    }


}
