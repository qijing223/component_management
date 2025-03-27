package com.lot.server.employee.mapper;


import com.lot.server.employee.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Results(id = "EmployeeMap", value = {
            @Result(column = "employee_id", property = "employee_id"),
            @Result(column = "employee_name", property = "employee_name"),
            @Result(column = "password", property = "password"),
            @Result(column = "department", property = "department"),
            @Result(column = "user_type", property = "user_type")
    })
    @Select("SELECT * FROM employee WHERE employee_id = #{id}")
    EmployeeEntity getEmployeeById(Integer id);

    @ResultMap("EmployeeMap")
    @Select("SELECT * FROM employee")
    List<EmployeeEntity> getAllEmployees();

    @Insert("INSERT INTO employee (employee_id, employee_name, department, password, user_type) " +
            "VALUES (#{employee_id}, #{employee_name}, #{department}, #{password}, #{user_type})")
    int addEmployee(EmployeeEntity employee);

    @Update("UPDATE employee SET employee_name=#{employee_name}, department=#{department}, " +
            "password=#{password}, user_type=#{user_type} WHERE employee_id=#{employee_id}")
    int updateEmployee(EmployeeEntity employee);

    @Delete("DELETE FROM employee WHERE employee_id=#{id}")
    int deleteEmployee(Integer id);

    @Select("SELECT password FROM employee WHERE employee_name = #{employeeName}")
    String getPasswordByUsername(String employeeName);
}
