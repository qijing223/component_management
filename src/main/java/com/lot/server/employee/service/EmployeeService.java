package com.lot.server.employee.service;


import com.lot.server.employee.domain.model.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(Integer id);
    List<EmployeeDTO> getAllEmployees();
    boolean addEmployee(EmployeeDTO employee, String rawpassword);
    boolean updateEmployee(EmployeeDTO employee);
    boolean deleteEmployee(Integer id);
}
