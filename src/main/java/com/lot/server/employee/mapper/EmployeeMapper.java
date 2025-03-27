package com.lot.server.employee.mapper;


import com.lot.server.employee.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Results(id = "EmployeeMap", value = {
            @Result(column = "employees_id", property = "employees_id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "department", property = "department"),
            @Result(column = "manager", property = "manager")
    })
    @Select("SELECT * FROM employee WHERE employees_id = #{id}")
    EmployeeEntity getEmployeeById(Integer id);

    @ResultMap("EmployeeMap")
    @Select("SELECT * FROM employee")
    List<EmployeeEntity> getAllEmployees();

    @Insert("INSERT INTO employee (username, password, department, manager) VALUES (#{username}, #{password}, #{department}, #{manager})")
    @Options(useGeneratedKeys = true, keyProperty = "employees_id")
    int addEmployee(EmployeeEntity employee);

    @Update("UPDATE employee SET username=#{username}, password=#{password}, department=#{department}, manager=#{manager} WHERE employees_id=#{employees_id}")
    int updateEmployee(EmployeeEntity employee);

    @Delete("DELETE FROM employee WHERE employees_id=#{id}")
    int deleteEmployee(Integer id);

    @Select("SELECT password FROM employee WHERE employee_name = #{employeeName}")
    String getPasswordByUsername(String employeeName);
}
