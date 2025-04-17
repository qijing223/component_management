package com.lot.server.employee.impl;

import com.lot.server.employee.domain.entity.EmployeeEntity;
import com.lot.server.employee.domain.model.EmployeeDTO;
import com.lot.server.employee.mapper.EmployeeMapper;
import com.lot.server.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@SpringBootTest
class EmployeeServiceImplTest {

    private EmployeeMapper employeeMapper;
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeMapper = mock(EmployeeMapper.class);
        employeeService = new EmployeeServiceImpl();
        employeeService.employeeMapper = employeeMapper;
    }

    @Test
    void testGetEmployeeById_found() {
        EmployeeEntity mockEntity = new EmployeeEntity();
        mockEntity.setEmployee_id(1);
        mockEntity.setEmployee_name("Alice");
        mockEntity.setDepartment("HR");
        mockEntity.setUser_type("admin");

        when(employeeMapper.getEmployeeById(1)).thenReturn(mockEntity);

        EmployeeDTO result = employeeService.getEmployeeById(1);
        assertNotNull(result);
        assertEquals("Alice", result.getEmployee_name());
        assertEquals("HR", result.getDepartment());
    }

    @Test
    void testGetEmployeeById_notFound() {
        when(employeeMapper.getEmployeeById(999)).thenReturn(null);
        EmployeeDTO result = employeeService.getEmployeeById(999);
        assertNull(result);
    }

    @Test
    void testGetAllEmployees() {
        EmployeeEntity e1 = new EmployeeEntity();
        e1.setEmployee_id(1);
        e1.setEmployee_name("A");

        EmployeeEntity e2 = new EmployeeEntity();
        e2.setEmployee_id(2);
        e2.setEmployee_name("B");

        when(employeeMapper.getAllEmployees()).thenReturn(Arrays.asList(e1, e2));

        List<EmployeeDTO> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getEmployee_name());
    }

    @Test
    void testAddEmployee_success() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployee_name("Alice");
        entity.setPassword("123456");

        when(employeeMapper.addEmployee(entity)).thenReturn(1);

        boolean added = employeeService.addEmployee(entity);
        assertTrue(added);
    }

    @Test
    void testAddEmployee_missingName_shouldFail() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setPassword("123456");

        boolean added = employeeService.addEmployee(entity);
        assertFalse(added);
        verify(employeeMapper, never()).addEmployee(any());
    }

    @Test
    void testUpdateEmployee_success() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployee_id(1);
        entity.setEmployee_name("Updated");

        when(employeeMapper.updateEmployee(entity)).thenReturn(1);

        boolean updated = employeeService.updateEmployee(entity);
        assertTrue(updated);
    }

    @Test
    void testUpdateEmployee_nullId_shouldFail() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployee_name("Updated");

        boolean updated = employeeService.updateEmployee(entity);
        assertFalse(updated);
        verify(employeeMapper, never()).updateEmployee(any());
    }

    @Test
    void testDeleteEmployee_success() {
        when(employeeMapper.deleteEmployee(1)).thenReturn(1);

        boolean deleted = employeeService.deleteEmployee(1);
        assertTrue(deleted);
    }

    @Test
    void testDeleteEmployee_nullId_shouldFail() {
        boolean deleted = employeeService.deleteEmployee(null);
        assertFalse(deleted);
        verify(employeeMapper, never()).deleteEmployee(any());
    }
}
