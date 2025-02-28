package com.lot.server.checkin.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Check-in/Check-out DTO")
public class CheckInDTO implements Serializable {

    @Schema(description = "Product ID")
    private Integer productId;

    @Schema(description = "Employee ID")
    private Integer employeeId;

    @Schema(description = "Serial Number")
    private String serialNumber;

    @Schema(description = "Action Type (CHECK_IN / CHECK_OUT)")
    private String action;

    @Schema(description = "Operation Time")
    private LocalDateTime operateTime;
}
