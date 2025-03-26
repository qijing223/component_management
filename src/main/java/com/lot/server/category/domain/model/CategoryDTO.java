package com.lot.server.category.domain.model;

public class CategoryDTO {

    private Integer productId;
    private String productName;
    private Integer numberPartCheckOut;
    private Integer numberPartInStock;
    private Double totalCost;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getNumberPartCheckOut() {
        return numberPartCheckOut;
    }

    public void setNumberPartCheckOut(Integer numberPartCheckOut) {
        this.numberPartCheckOut = numberPartCheckOut;
    }

    public Integer getNumberPartInStock() {
        return numberPartInStock;
    }

    public void setNumberPartInStock(Integer numberPartInStock) {
        this.numberPartInStock = numberPartInStock;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
