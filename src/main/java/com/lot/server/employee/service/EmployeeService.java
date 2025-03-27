package com.lot.server.employee.service;


import com.lot.server.employee.domain.entity.EmployeeEntity;
import com.lot.server.employee.domain.model.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(Integer id);
    List<EmployeeDTO> getAllEmployees();
    boolean addEmployee(EmployeeEntity employee);  // 修改为只接收一个参数
    boolean updateEmployee(EmployeeEntity employee);
    boolean deleteEmployee(Integer id);
}
