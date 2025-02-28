package com.lot.server.api;

import com.lot.server.checkin.domain.model.CheckInDTO;
import com.lot.server.checkin.domain.model.ItemStatusDTO;
import com.lot.server.checkin.service.CheckInService;
import com.lot.server.common.bean.ResultTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@Tag(name = "Check-in API", description = "Manages item check-in and check-out operations")
public class CheckInApi {

    private final CheckInService checkInService;

    public CheckInApi(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    /**
     * Employee borrows an item (Check-out).
     *
     * @param checkInDTO Data transfer object containing employee and product details.
     * @return ResultTO<Void> indicating success or failure.
     */
    @PostMapping("/borrow")
    @Operation(summary = "Borrow an item", description = "Allows an employee to borrow an item")
    public ResponseEntity<ResultTO<Void>> borrowItem(@Valid @RequestBody CheckInDTO checkInDTO) {
        try {
            return ResponseEntity.ok(checkInService.checkOut(checkInDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResultTO.error(e.getMessage()));
        }
    }

    /**
     * Employee returns an item (Check-in).
     *
     * @param checkInDTO Data transfer object containing employee and product details.
     * @return ResultTO<Void> indicating success or failure.
     */
    @PostMapping("/return")
    @Operation(summary = "Return an item", description = "Allows an employee to return an item")
    public ResponseEntity<ResultTO<Void>> returnItem(@Valid @RequestBody CheckInDTO checkInDTO) {
        try {
            return ResponseEntity.ok(checkInService.checkIn(checkInDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResultTO.error(e.getMessage()));
        }
    }

    /**
     * Check the current status of an item.
     *
     * @param productId The ID of the product.
     * @param serialNumber The unique serial number of the product item.
     * @return ResultTO<ItemStatusDTO> containing detailed item status.
     */
    @GetMapping("/status")
    @Operation(summary = "Check item status", description = "Retrieves the check-out status of a specific item")
    public ResponseEntity<ResultTO<ItemStatusDTO>> checkStatus(@RequestParam Integer productId, @RequestParam String serialNumber) {
        try {
            return ResponseEntity.ok(checkInService.checkStatus(productId, serialNumber));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResultTO.error(e.getMessage()));
        }
    }

}
