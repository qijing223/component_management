package com.lot.server.component.domain.model;
import com.lot.server.component.domain.entity.ComponentStatus;

public class ComponentDTO {
    private Integer productsId;
    private String productName;
    private Integer category;
    private ComponentStatus status;

    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public ComponentStatus getStatus() {
        return status;
    }

    public void setStatus(ComponentStatus status) {
        this.status = status;
    }
}

