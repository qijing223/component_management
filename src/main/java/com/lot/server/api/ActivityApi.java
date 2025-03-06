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
        String categoryName = (String) requestData.get("categoryName");
        String productName = (String) requestData.get("productName");
        Integer userId = (Integer) requestData.get("userId");

        if (categoryName == null || userId == null) {
            return ResponseEntity.badRequest().body("Category name and user ID are required.");
        }

        // Step 1: Check if the category exists, otherwise create a new one
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

        // Step 2: Create a new product (ComponentDTO) under this category
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setProductName(productName);
        // TODO: componentDTO's setCategory method should link category ID not string
        componentDTO.setCategory(categoryDTO.getCategoryId());
        componentService.createProduct(componentDTO); // Assuming this method creates and assigns an ID

        // Step 3: Create an activity record for stock-in
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(componentDTO.getProductsId());
        activityDTO.setEmployeeId(userId);
        activityDTO.setAction(ActivityAction.STOCK_IN);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Save activity record
        activityService.stockIn(activityDTO);

        return ResponseEntity.ok("Stock-in operation successful.");
    }


    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody ActivityDTO activityDTO) {
        activityService.borrow(activityDTO);
        return ResponseEntity.ok("Borrow operation successful.");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody ActivityDTO activityDTO) {
        activityService.returnItem(activityDTO);
        return ResponseEntity.ok("Return operation successful.");
    }
}
