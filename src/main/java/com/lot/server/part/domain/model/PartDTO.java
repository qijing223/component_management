package com.lot.server.part.domain.model;

import com.lot.server.part.domain.entity.PartStatus;
import lombok.Data;

@Data
public class PartDTO {
    private Integer partNumber;
    private Integer partId;
    private String partName;
    private Integer borrowedEmployeeId;
    private String status;
    private Double cost;
    private Integer productId;
}
