package com.lot.server.component.domain.model;

import com.lot.server.component.domain.entity.ComponentStatus;

public class ComponentDTO {
    private Integer partId;
    private ComponentStatus status; // 假设你有一个枚举表示状态
    private String productName;
    private Integer productId;
    private Double cost;
    private String partName;
    private Integer borrowedEmployeeId;

    @Override
    public String toString() {
        return "ComponentDTO{" +
                "partId=" + partId +
                ", status=" + status +
                ", productName='" + productName + '\'' +
                ", productId=" + productId +
                ", cost=" + cost +
                ", partName='" + partName + '\'' +
                ", borrowedEmployeeId=" + borrowedEmployeeId +
                '}';
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public ComponentStatus getStatus() {
        return status;
    }

    public void setStatus(ComponentStatus status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getBorrowedEmployeeId() {
        return borrowedEmployeeId;
    }

    public void setBorrowedEmployeeId(Integer borrowedEmployeeId) {
        this.borrowedEmployeeId = borrowedEmployeeId;
    }
}
