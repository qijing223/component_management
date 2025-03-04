package com.lot.server.activity.mapper;

import com.lot.server.activity.domain.entity.EmployeeActivity;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * MyBatis Mapper interface for EmployeeActivity database operations.
 */
@Mapper
public interface ActivityMapper {

    /**
     * Retrieves an EmployeeActivity by its ID.
     * @param activityId the ID of the EmployeeActivity
     * @return the corresponding EmployeeActivity entity
     */
    @Select("SELECT * FROM employee_activity WHERE activity_id = #{activityId}")
    EmployeeActivity selectById(@Param("activityId") Integer activityId);

    /**
     * Retrieves all EmployeeActivity records.
     * @return list of all EmployeeActivity entities
     */
    @Select("SELECT * FROM employee_activity")
    List<EmployeeActivity> selectAll();

    /**
     * Inserts a new EmployeeActivity record.
     * @param employeeActivity the EmployeeActivity entity to be inserted
     */
    @Insert("INSERT INTO employee_activity (product_id, employee_id, action, operate_time) " +
            "VALUES (#{productId}, #{employeeId}, #{action}, #{operateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "activityId", keyColumn = "activity_id")
    void insert(EmployeeActivity employeeActivity);

    /**
     * Updates an existing EmployeeActivity record.
     * @param employeeActivity the EmployeeActivity entity with updated data
     */
    @Update("UPDATE employee_activity SET product_id = #{productId}, employee_id = #{employeeId}, " +
            "action = #{action}, operate_time = #{operateTime} WHERE activity_id = #{activityId}")
    void update(EmployeeActivity employeeActivity);

    /**
     * Deletes an EmployeeActivity record by its ID.
     * @param activityId the ID of the EmployeeActivity to be deleted
     */
    @Delete("DELETE FROM employee_activity WHERE activity_id = #{activityId}")
    void deleteById(@Param("activityId") Integer activityId);
}