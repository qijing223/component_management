package com.lot.server.checkin.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "equipment_usage_list")
public class EquipmentUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Integer usageId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EquipmentStatus status;

    @Column(name = "operate_time", nullable = false)
    private LocalDateTime operateTime;
}
