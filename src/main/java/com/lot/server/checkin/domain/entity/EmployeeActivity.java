package com.lot.server.checkin.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employee_activity")
public class EmployeeActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer activityId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActivityAction action;

    @Column(name = "product_number", nullable = false)
    private Integer productNumber;

    @Column(name = "operate_time", nullable = false)
    private LocalDateTime operateTime;
}
