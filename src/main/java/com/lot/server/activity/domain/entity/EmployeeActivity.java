package com.lot.server.activity.domain.entity;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActivityAction action;

    @Column(name = "operate_time", nullable = false)
    private LocalDateTime operateTime;

    @Column(name = "part_id", nullable = false)
    private Integer partId;
}
