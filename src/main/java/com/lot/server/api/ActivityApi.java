package com.lot.server.api;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.service.ActivityService;
import com.lot.server.product.domain.model.ProductDTO;
import com.lot.server.product.service.ProductService;
import com.lot.server.common.bean.ResultTO;
import com.lot.server.common.context.UserContext;
import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityApi {
    private final ActivityService activityService;
    private final ProductService productService;
    private final PartService partService; // Assuming product is represented by ComponentDTO

    @Autowired
    public ActivityApi(ActivityService activityService,
                       ProductService productService,
                       PartService partService) {
        this.activityService = activityService;
        this.productService = productService;
        this.partService = partService;
    }

    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(@RequestBody Map<String, Object> requestData) {
        System.out.println("Received request data: " + requestData);
        Integer partId = (Integer) requestData.get("part_id");
        Integer productId = (Integer) requestData.get("product_id");
        Integer employeeId = UserContext.getUserId();
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

//        CategoryDTO categoryDTO = productService.getCategoryById(productId);
//        if (categoryDTO == null) {
//            return ResponseEntity.badRequest().body("Product not found for product id, please create product first: " + productId);
//        }

        // TODO change to PartDTO
//        ComponentDTO componentDTO = new ComponentDTO();
//        componentDTO.setPartId(partId);
//        componentDTO.setProductId(productId);
//        componentDTO.setStatus(PartStatus.AVAILABLE);
//        componentDTO.setCost(cost);
//        componentDTO.setProductName(categoryDTO.getProductName());
//        componentDTO.setPartName(partName);
//
//        try {
//            partService.createPart(componentDTO);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Failed to create part: " + e.getMessage());
//        }

//        categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() + 1);
//        categoryDTO.setTotalCost(categoryDTO.getTotalCost() + cost);
//        productService.updateCategory(categoryDTO);
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
        Integer employeeId = UserContext.getUserId();

        if (partId == null || employeeId == null) {
            return ResponseEntity.badRequest().body("part_id and employee_id are required.");
        }

        // 获取零件详情
        PartDTO partDTO = partService.getPartById(partId);
        if (partDTO == null) {
            return ResponseEntity.badRequest().body("Part not found.");
        }

        String debugInfo = String.format("Debug Info:\nCurrent part status: %s\nExpected status: %s\nStatus comparison: %b",
                partDTO.getStatus(), PartStatus.AVAILABLE, partDTO.getStatus() == PartStatus.AVAILABLE);

        // 检查零件是否可借用
        if (partDTO.getStatus() != PartStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body("Parts is not available for borrowing. Current status: " + partDTO.getStatus());
        }

        // 获取产品详情
        Map<String, Object> productId = partDTO.getProductId();
        if (productId != null) {
            ProductDTO productDTO = productService.getProductById(productId);
            if (productDTO != null) {
                productDTO.setNumberPartInStock(productDTO.getNumberPartInStock() - 1);
                productDTO.setNumberPartCheckOut(productDTO.getNumberPartCheckOut() + 1);
                productService.updateProduct(productDTO);
            }
        }

        // 更新零件状态为"已借出"
        partDTO.setStatus(PartStatus.BORROW_OUT);
        partDTO.setBorrowedEmployeeId(employeeId);
        partService.updatePart(partId, partDTO);
        partService.updateBorrowedByPartId(employeeId, partId);

        // 创建活动记录
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
//        Integer partId = (Integer) requestData.get("part_id");
//        Integer employeeId = UserContext.getUserId();
//
//        if (partId == null || employeeId == null) {
//            return ResponseEntity.badRequest().body("Product ID and User ID are required.");
//        }
//
//        // Step 1: Retrieve product details
//        ComponentDTO componentDTO = partService.getProductById(partId);
//        if (componentDTO == null) {
//            return ResponseEntity.badRequest().body("Product not found.");
//        }
//
//        // System.out.println("Current part status: " + componentDTO.getStatus());
//        // System.out.println("Expected status: " + ComponentStatus.BORROW_OUT);
//        // System.out.println("Status comparison: " + (componentDTO.getStatus() == ComponentStatus.BORROW_OUT));
//
//        // Step 2: Check if the product is actually borrowed
//        if (componentDTO.getStatus() != PartStatus.BORROW_OUT) {
//            return ResponseEntity.badRequest().body("Part is not currently borrowed. Current status: " + componentDTO.getStatus());
//        }
//
//        // Step 3: Check if the current user is the one who borrowed the item
//        if (!employeeId.equals(componentDTO.getBorrowedEmployeeId())) {
//            return ResponseEntity.badRequest().body("Only the user who borrowed this item can return it.");
//        }
//
//        // Step 4: Retrieve category details using category ID
//        Integer productId = componentDTO.getProductId();
//        if (productId != null) {
//            CategoryDTO categoryDTO = categoryService.getCategoryById(productId);
//            if (categoryDTO != null) {
//                // Step 5: Increase category available count
//                categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() + 1);
//                categoryDTO.setNumberPartCheckOut(categoryDTO.getNumberPartCheckOut() - 1);
//                categoryService.updateCategory(categoryDTO);
//            }
//        }
//
//        // Step 6: Update product status back to "Available"
//        componentDTO.setStatus(PartStatus.AVAILABLE);
//        componentDTO.setBorrowedEmployeeId(null); // Clear the borrowed employee ID
//        partService.updateProduct(partId, componentDTO);
//        partService.createReturnedByPartId(employeeId, partId);
//
//        // Step 7: Create an activity record
//        ActivityDTO activityDTO = new ActivityDTO();
//        activityDTO.setPartId(partId);
//        activityDTO.setProductId(productId);
//        activityDTO.setEmployeeId(employeeId);
//        activityDTO.setAction(ActivityAction.RETURN);
//        activityDTO.setOperateTime(LocalDateTime.now());
//
//        activityService.returnItem(activityDTO);

        return ResponseEntity.ok("Return operation successful.");
    }

    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(@RequestBody Map<String, Object> requestData) {
//        Integer partId = (Integer) requestData.get("part_id");
//        Integer productId = (Integer) requestData.get("product_id");
//        Integer employeeId = (Integer) requestData.get("employee_id");
//
//        if (partId == null || employeeId == null || productId == null) {
//            return ResponseEntity.badRequest().body("part_id, product_id, and employee_id are required.");
//        }
//
//        // Step 1: Retrieve product details
//        ComponentDTO componentDTO = partService.getProductById(partId);
//        if (componentDTO == null) {
//            return ResponseEntity.badRequest().body("Product not found.");
//        }
//
//        // Step 2: Check if the product is available for stock-out
//        if (componentDTO.getStatus() != PartStatus.AVAILABLE) {
//            return ResponseEntity.badRequest().body("Product is not available for stock-out.");
//        }
//
//        // Step 3: Retrieve category details using category ID
//        CategoryDTO categoryDTO = categoryService.getCategoryById(productId);
//        if (categoryDTO != null) {
//            categoryDTO.setNumberPartInStock(categoryDTO.getNumberPartInStock() - 1);
//            categoryDTO.setTotalCost(categoryDTO.getTotalCost() - componentDTO.getCost());
//            categoryService.updateCategory(categoryDTO);
//        }
//
//        // Step 4: Update product status to "Unavailable"
//        componentDTO.setStatus(PartStatus.UNAVAILABLE);
//        partService.updateProduct(partId, componentDTO);
//
//        // Step 5: Create an activity record
//        ActivityDTO activityDTO = new ActivityDTO();
//        activityDTO.setPartId(partId);
//        activityDTO.setProductId(productId);
//        activityDTO.setEmployeeId(employeeId);
//        activityDTO.setAction(ActivityAction.STOCK_OUT);
//        activityDTO.setOperateTime(LocalDateTime.now());
//
//        activityService.stockOut(activityDTO);

        return ResponseEntity.ok("Stock-out operation successful.");
    }

    @GetMapping
    public ResultTO<List<ActivityDTO>> getActivities() {
        Integer userId = UserContext.getUserId();
        System.out.println("Get Activities from UserId: " + userId);
        List<ActivityDTO> activities = activityService.getActivities(userId);

        return ResultTO.success(activities);
    }

}
