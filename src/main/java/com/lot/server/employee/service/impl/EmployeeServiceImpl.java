package com.lot.server.employee.service.impl;
import com.lot.server.employee.domain.entity.EmployeeEntity;
import com.lot.server.employee.domain.model.EmployeeDTO;
import com.lot.server.employee.mapper.EmployeeMapper;
import com.lot.server.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        EmployeeEntity employee = employeeMapper.getEmployeeById(id);
        return (employee != null) ? convertToDTO(employee) : null;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeMapper.getAllEmployees();
        return (employees != null)
                ? employees.stream().map(this::convertToDTO).collect(Collectors.toList())
                : List.of();
    }

    @Override
    public boolean addEmployee(EmployeeEntity employee) {
        if (employee == null || employee.getEmployee_name() == null || employee.getPassword() == null) {
            return false;
        }
        return employeeMapper.addEmployee(employee) > 0;
    }

    @Override
    public boolean updateEmployee(EmployeeEntity employee) {
        if (employee == null || employee.getEmployee_id() == null) {
            return false;
        }

        return employeeMapper.updateEmployee(employee) > 0;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        return (id != null) && employeeMapper.deleteEmployee(id) > 0;
    }

    private EmployeeDTO convertToDTO(EmployeeEntity entity) {
        return new EmployeeDTO(entity.getEmployee_id(), entity.getEmployee_name(), entity.getDepartment(), entity.getUser_type());
    }

    private EmployeeEntity convertToEntity(EmployeeDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployee_id(dto.getEmployee_id());
        entity.setEmployee_name(dto.getEmployee_name());
        entity.setDepartment(dto.getDepartment());
        entity.setUser_type(dto.getUser_type());
        return entity;
    }
}
