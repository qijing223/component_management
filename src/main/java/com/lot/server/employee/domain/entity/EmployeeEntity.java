package com.lot.server.employee.domain.entity;


import lombok.Data;
import java.io.Serializable;

@Data
public class EmployeeEntity implements Serializable{
    private Integer employee_id;
    private String employee_name;
    private String password;
    private String department;
    private String user_type;
}
