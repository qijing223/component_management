package com.lot.server.product.domain.entity;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProductDO {
    private Integer productId;
    private String productName;
    private Integer numberPartInStock;
    private Double totalCost;
    private Integer numberPartCheckOut;
    private Date leadTime;
    private Map<String, Object> partList;
}
