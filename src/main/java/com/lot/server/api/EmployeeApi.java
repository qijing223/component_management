package com.lot.server.api;

import com.lot.server.employee.domain.model.EmployeeDTO;
import com.lot.server.employee.domain.entity.EmployeeEntity;
import com.lot.server.employee.service.EmployeeService;
import com.lot.server.common.bean.ResultTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeApi {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public ResultTO<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return (employees != null && !employees.isEmpty())
                ? ResultTO.success(employees)
                : ResultTO.error("No employees found");
    }


    @GetMapping("/{id}")
    public ResultTO<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return (employee != null)
                ? ResultTO.success(employee)
                : ResultTO.error("Employee not found");
    }


    @PostMapping("/")
    public ResultTO<String> addEmployee(@RequestBody EmployeeEntity employee) {
        boolean isAdded = employeeService.addEmployee(employee);
        return isAdded
                ? ResultTO.success("Employee added successfully")
                : ResultTO.error("Failed to add employee");
    }

    @PutMapping("/")
    public ResultTO<String> updateEmployee(@RequestBody EmployeeEntity employee) {
        boolean isUpdated = employeeService.updateEmployee(employee);
        return isUpdated
                ? ResultTO.success("Employee updated successfully")
                : ResultTO.error("Failed to update employee");
    }


    @DeleteMapping("/{id}")
    public ResultTO<String> deleteEmployee(@PathVariable Integer id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        return isDeleted
                ? ResultTO.success("Employee deleted successfully")
                : ResultTO.error("Failed to delete employee");
    }
}
