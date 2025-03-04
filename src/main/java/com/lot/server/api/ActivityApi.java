package com.lot.server.api;

import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.domain.entity.EmployeeActivity;
import com.lot.server.activity.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for managing Employee Activities.
 */
@RestController
@RequestMapping("/api/activities")
public class ActivityApi {

    private final ActivityService activityService;

    public ActivityApi(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Endpoint to handle stock-in operation.
     * @param activityDTO the DTO containing stock-in details
     * @return success message
     */
    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(@RequestBody ActivityDTO activityDTO) {
        activityService.stockIn(activityDTO);
        return ResponseEntity.ok("Stock-in operation successful.");
    }

    /**
     * Endpoint to handle borrow operation.
     * @param activityDTO the DTO containing borrow details
     * @return success message
     */
    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody ActivityDTO activityDTO) {
        activityService.borrow(activityDTO);
        return ResponseEntity.ok("Borrow operation successful.");
    }

    /**
     * Endpoint to handle return operation.
     * @param activityDTO the DTO containing return details
     * @return success message
     */
    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody ActivityDTO activityDTO) {
        activityService.returnItem(activityDTO);
        return ResponseEntity.ok("Return operation successful.");
    }
}
