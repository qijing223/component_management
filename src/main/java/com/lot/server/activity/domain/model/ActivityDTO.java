package com.lot.server.activity.domain.model;

import com.lot.server.activity.domain.entity.ActivityAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Check-in/Check-out DTO")
public class ActivityDTO implements Serializable {

    @Schema(description = "Product ID")
    private Integer productId;

    @Schema(description = "Part ID")
    private Integer partId;

    @Schema(description = "Employee ID")
    private Integer employeeId;

    @Schema(description = "Action Type (Stock-in, Stock-out, Borrow, Return, Dispose)")
    private ActivityAction action;

    @Schema(description = "Operation Time")
    private LocalDateTime operateTime;
}
