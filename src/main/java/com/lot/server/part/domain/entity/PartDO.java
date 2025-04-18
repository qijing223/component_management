package com.lot.server.part.domain.entity;

import lombok.Data;

@Data
public class PartDO {
    private Integer partNumber;
    private Integer partId;
    private String partName;
    private Integer borrowedEmployeeId;
    private PartStatus status;
    private Double cost;
    private Integer productId;
}
