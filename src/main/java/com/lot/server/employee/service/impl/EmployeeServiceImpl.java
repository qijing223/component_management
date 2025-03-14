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
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    public boolean addEmployee(EmployeeDTO employee,String rawpassword) {
        if (employee == null || employee.getUsername() == null || rawpassword == null) {
            return false;
        }
        EmployeeEntity entity = convertToEntity(employee);
//        String hashedPassword = passwordEncoder.encode(rawpassword);
        entity.setPassword(rawpassword);

        return employeeMapper.addEmployee(entity) > 0;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employee) {//still not add function of update password.
        if (employee == null || employee.getEmployees_id() == null) {
            return false;
        }
        EmployeeEntity entity = convertToEntity(employee);
        return employeeMapper.updateEmployee(entity) > 0;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        return (id != null) && employeeMapper.deleteEmployee(id) > 0;
    }

    private EmployeeDTO convertToDTO(EmployeeEntity entity) {
        return new EmployeeDTO(entity.getEmployees_id(), entity.getUsername(), entity.getDepartment(), entity.getManager());
    }

    private EmployeeEntity convertToEntity(EmployeeDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployees_id(dto.getEmployees_id());
        entity.setUsername(dto.getUsername());
        entity.setDepartment(dto.getDepartment());
        entity.setManager(dto.getManager());
        return entity;
    }
}
