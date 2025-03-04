package com.lot.server.employee.domain.entity;


import lombok.Data;
import java.io.Serializable;

@Data
public class EmployeeEntity implements Serializable{
    private Integer employees_id;
    private String username;
    private String password;
    private String department;
    private String manager;
}
