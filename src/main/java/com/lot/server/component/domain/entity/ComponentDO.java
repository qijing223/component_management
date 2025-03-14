package com.lot.server.component.domain.entity;
import com.lot.server.component.domain.entity.ComponentStatus;

public class ComponentDO {
    private Integer productsId;
    private Integer category;
    private ComponentStatus status;

    public Integer getProductsId() {
        return productsId;
    }

    public void setProductsId(Integer productsId) {
        this.productsId = productsId;
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