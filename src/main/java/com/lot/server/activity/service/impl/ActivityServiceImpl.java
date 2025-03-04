package com.lot.server.activity.service.impl;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.entity.EmployeeActivity;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.mapper.ActivityMapper;
import com.lot.server.activity.service.ActivityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of ActivityService for managing employee activities.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper employeeActivityMapper;

    public ActivityServiceImpl(ActivityMapper employeeActivityMapper) {
        this.employeeActivityMapper = employeeActivityMapper;
    }

    @Override
    public void stockIn(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.STOCK_IN);
    }

    @Override
    public void borrow(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.BORROW);
    }

    @Override
    public void returnItem(ActivityDTO activityDTO) {
        insertActivity(activityDTO, ActivityAction.RETURN);
    }

    /**
     * Inserts a new employee activity record into the database.
     * @param activityDTO the DTO containing activity details
     * @param action the action type (STOCK_IN, BORROW, RETURN)
     */
    private void insertActivity(ActivityDTO activityDTO, ActivityAction action) {
        EmployeeActivity employeeActivity = new EmployeeActivity();
        employeeActivity.setProductId(activityDTO.getProductId());
        employeeActivity.setEmployeeId(activityDTO.getEmployeeId());
        employeeActivity.setAction(action);
        employeeActivity.setOperateTime(LocalDateTime.now());

        employeeActivityMapper.insert(employeeActivity);
    }
}
