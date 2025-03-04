package com.lot.server.category.domain.entity;

public class CategoryDO {
    private Integer categoryId;
    private String categoryName;
    private Integer availableNumber;
    private Integer usageNumber;
    private Integer borrowNumber;

    // Getters and Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(Integer availableNumber) {
        this.availableNumber = availableNumber;
    }

    public Integer getUsageNumber() {
        return usageNumber;
    }

    public void setUsageNumber(Integer usageNumber) {
        this.usageNumber = usageNumber;
    }

    public Integer getBorrowNumber() {
        return borrowNumber;
    }

    public void setBorrowNumber(Integer borrowNumber) {
        this.borrowNumber = borrowNumber;
    }
}