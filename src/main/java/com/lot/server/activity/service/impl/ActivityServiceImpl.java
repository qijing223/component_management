package com.lot.server.activity.service.impl;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.entity.EmployeeActivity;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.mapper.ActivityMapper;
import com.lot.server.activity.service.ActivityService;
import com.lot.server.employee.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ActivityService for managing employee activities.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper employeeActivityMapper;
    private final EmployeeMapper employeeMapper;

    public ActivityServiceImpl(ActivityMapper employeeActivityMapper, EmployeeMapper employeeMapper) {
        this.employeeActivityMapper = employeeActivityMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public void stockIn(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.STOCK_IN);
    }

    @Override
    public void stockOut(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.STOCK_OUT);
    }

    @Override
    public void borrow(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.BORROW);
    }

    @Override
    public void returnItem(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.RETURN);
    }

    @Override
    public List<ActivityDTO> getActivities(Integer employeeId) {
        String userType = employeeMapper.getEmployeeTypeById(employeeId);
        if(userType.equals("Admin")){
            return getAllActivities();
        } else {
            return getUserActivities(employeeId);
        }
    }

    private List<ActivityDTO> getAllActivities() {
        List<EmployeeActivity> activities = employeeActivityMapper.selectAll();
        return activities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private List<ActivityDTO> getUserActivities(Integer userId) {
        List<EmployeeActivity> activities = employeeActivityMapper.selectByEmployeeId(userId);
        return activities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Inserts a new employee activity record into the database.
     * @param activityDTO the DTO containing activity details
     * @param action the action type (STOCK_IN, STOCK_OUT, BORROW, RETURN)
     */
    private void insertActivity(ActivityDTO activityDTO, ActivityAction action) {
        EmployeeActivity employeeActivity = new EmployeeActivity();
        employeeActivity.setProductId(activityDTO.getProductId());
        employeeActivity.setEmployeeId(activityDTO.getEmployeeId());
        employeeActivity.setPartId(activityDTO.getPartId());
        employeeActivity.setAction(action);
        employeeActivity.setOperateTime(LocalDateTime.now());

        employeeActivityMapper.insert(employeeActivity);
    }

    /**
     * Converts EmployeeActivity entity to ActivityDTO
     * @param activity the EmployeeActivity entity
     * @return the converted ActivityDTO
     */
    private ActivityDTO convertToDTO(EmployeeActivity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setProductId(activity.getProductId());
        dto.setEmployeeId(activity.getEmployeeId());
        dto.setPartId(activity.getPartId());
        dto.setAction(activity.getAction());
        dto.setOperateTime(activity.getOperateTime());
        return dto;
    }
}
