package com.lot.server.part.domain.entity;

import lombok.Data;

import java.util.Map;

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
