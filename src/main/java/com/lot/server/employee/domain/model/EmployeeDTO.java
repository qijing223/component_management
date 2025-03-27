package com.lot.server.employee.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer employee_id;  // ✅ 按照数据库字段
    private String employee_name;
    private String department;
    private String user_type;
}
