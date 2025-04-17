package com.lot.server.part.domain.model;

import java.time.LocalDateTime;

public class ReturnedDTO {
    private Integer partId;

    private String partName;

    private LocalDateTime returnTime;

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getBorrowEmployeeId() {
        return borrowEmployeeId;
    }

    public void setBorrowEmployeeId(Integer borrowEmployeeId) {
        this.borrowEmployeeId = borrowEmployeeId;
    }

    private Integer borrowEmployeeId;
}
