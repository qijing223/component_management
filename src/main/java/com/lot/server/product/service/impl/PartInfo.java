package com.lot.server.product.service.impl;

public class PartInfo {
    private Integer part_number;
    private String name;
    private Integer quantity;

    // Getter and Setter
    public Integer getPart_number() {
        return part_number;
    }

    public void setPart_number(Integer part_number) {
        this.part_number = part_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
