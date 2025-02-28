package com.lot.server.checkin.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Item Status DTO")
public class ItemStatusDTO implements Serializable {

    @Schema(description = "Is the item currently checked out?")
    private boolean checkedOut;

    @Schema(description = "Employee ID who checked out the item")
    private Integer borrowedBy;

    @Schema(description = "Check-out time (if applicable)")
    private LocalDateTime borrowedAt;
}
