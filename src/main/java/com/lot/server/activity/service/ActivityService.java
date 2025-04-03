package com.lot.server.activity.service;

import com.lot.server.activity.domain.entity.EmployeeActivity;
import com.lot.server.activity.domain.model.ActivityDTO;

import java.util.List;

/**
 * Service interface for handling EmployeeActivity-related operations.
 */
public interface ActivityService {

    /**
     * Handles stock-in operation.
     * @param activityDTO the DTO containing stock-in details
     */
    void stockIn(ActivityDTO activityDTO);

    /**
     * Handles stock-out operation.
     * @param activityDTO the DTO containing stock-out details
     */
    void stockOut(ActivityDTO activityDTO);

    /**
     * Handles borrow operation.
     * @param activityDTO the DTO containing borrow details
     */
    void borrow(ActivityDTO activityDTO);

    /**
     * Handles return operation.
     * @param activityDTO the DTO containing return details
     */
    void returnItem(ActivityDTO activityDTO);

    /**
     * Retrieves all activity records.
     * @return list of all activity records
     */

    List<ActivityDTO> getActivities(Integer userId);
}
