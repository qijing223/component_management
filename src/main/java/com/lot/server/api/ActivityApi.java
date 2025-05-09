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

        // Get product details
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO == null) {
            return ResponseEntity.badRequest().body("Product not found for product id, please create product first: " + productId);
        }

        // Create part
        PartDTO partDTO = new PartDTO();
        partDTO.setPartId(partId);
        partDTO.setProductId(productId);
        partDTO.setStatus(PartStatus.AVAILABLE);
        partDTO.setCost(cost);
        partDTO.setPartName(partName);

        try {
            partService.createPart(partDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create part: " + e.getMessage());
        }

        // Update product inventory information
        productDTO.setNumberPartInStock(productDTO.getNumberPartInStock() + 1);
        productDTO.setTotalCost(productDTO.getTotalCost() + cost);
        productService.updateProduct(productDTO);

        // Create stock-in activity record
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

        // Get part details
        PartDTO partDTO = partService.getPartById(partId);
        if (partDTO == null) {
            return ResponseEntity.badRequest().body("Part not found.");
        }

        String debugInfo = String.format("Debug Info:\nCurrent part status: %s\nExpected status: %s\nStatus comparison: %b",
                partDTO.getStatus(), PartStatus.AVAILABLE, partDTO.getStatus() == PartStatus.AVAILABLE);

        // Check if part is available for borrowing
        if (partDTO.getStatus() != PartStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body("Parts is not available for borrowing. Current status: " + partDTO.getStatus());
        }

        // Get product details
        Integer productId = partDTO.getProductId();
        if (productId != null) {
            ProductDTO productDTO = productService.getProductById(productId);
            if (productDTO != null) {
                productDTO.setNumberPartInStock(productDTO.getNumberPartInStock() - 1);
                productDTO.setNumberPartCheckOut(productDTO.getNumberPartCheckOut() + 1);
                productService.updateProduct(productDTO);
            }
        }

        // Update part status to "Borrowed"
        partDTO.setStatus(PartStatus.BORROW_OUT);
        partDTO.setBorrowedEmployeeId(employeeId);
        partService.updatePart(partId, partDTO);
        partService.updateBorrowedByPartId(employeeId, partId);

        // Create activity record
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
        Integer employeeId = UserContext.getUserId();

        if (partId == null || employeeId == null) {
            return ResponseEntity.badRequest().body("part_id and employee_id are required.");
        }

        // Get part details
        PartDTO partDTO = partService.getPartById(partId);
        if (partDTO == null) {
            return ResponseEntity.badRequest().body("Part not found.");
        }

        // Check if part is currently borrowed
        if (partDTO.getStatus() != PartStatus.BORROW_OUT) {
            return ResponseEntity.badRequest().body("Part is not currently borrowed. Current status: " + partDTO.getStatus());
        }

        // Check if current user is the one who borrowed the item
        if (!employeeId.equals(partDTO.getBorrowedEmployeeId())) {
            return ResponseEntity.badRequest().body("Only the user who borrowed this item can return it.");
        }

        // Get product details
        Integer productId = partDTO.getProductId();
        if (productId != null) {
            ProductDTO productDTO = productService.getProductById(productId);
            if (productDTO != null) {
                // Update product inventory information
                productDTO.setNumberPartInStock(productDTO.getNumberPartInStock() + 1);
                productDTO.setNumberPartCheckOut(productDTO.getNumberPartCheckOut() - 1);
                productService.updateProduct(productDTO);
            }
        }

        // Update part status to "Available"
        partDTO.setStatus(PartStatus.AVAILABLE);
        //partDTO.setBorrowedEmployeeId(null); // Clear the borrowed employee ID
        partService.updatePart(partId, partDTO);
        partService.createReturnedByPartId(employeeId, partId);

        // Create activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setPartId(partId);
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(employeeId);
        activityDTO.setAction(ActivityAction.RETURN);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.returnItem(activityDTO);

        return ResponseEntity.ok("Return operation successful.");
    }

    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(@RequestBody Map<String, Object> requestData) {
        Integer partId = (Integer) requestData.get("part_id");
        Integer productId = (Integer) requestData.get("product_id");
        Integer employeeId = UserContext.getUserId();

        if (partId == null || employeeId == null || productId == null) {
            return ResponseEntity.badRequest().body("part_id, product_id, and employee_id are required.");
        }

        // Get part details
        PartDTO partDTO = partService.getPartById(partId);
        if (partDTO == null) {
            return ResponseEntity.badRequest().body("Part not found.");
        }

        // Check if part is available for stock-out
        if (partDTO.getStatus() != PartStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body("Part is not available for stock-out. Current status: " + partDTO.getStatus());
        }

        // Get product details
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO != null) {
            // Update product inventory information
            productDTO.setNumberPartInStock(productDTO.getNumberPartInStock() - 1);
            productDTO.setTotalCost(productDTO.getTotalCost() - partDTO.getCost());
            productService.updateProduct(productDTO);
        }

        // Update part status to "Unavailable"
        partDTO.setStatus(PartStatus.UNAVAILABLE);
        partService.updatePart(partId, partDTO);

        // Create activity record
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setPartId(partId);
        activityDTO.setProductId(productId);
        activityDTO.setEmployeeId(employeeId);
        activityDTO.setAction(ActivityAction.STOCK_OUT);
        activityDTO.setOperateTime(LocalDateTime.now());

        activityService.stockOut(activityDTO);

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
