package com.lot.server.activity.service;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ActivityService
 */
@Disabled
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ActivityServiceIntegrationTest {

    @Autowired
    private ActivityService activityService;

    @Test
    void testStockInFlow() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());
        activityDTO.setAction(ActivityAction.STOCK_IN);

        // Act
        activityService.stockIn(activityDTO);
        List<ActivityDTO> activities = activityService.getActivities(1);

        // Assert
        assertFalse(activities.isEmpty());
        ActivityDTO savedActivity = activities.get(0);
        assertEquals(ActivityAction.STOCK_IN, savedActivity.getAction());
        assertEquals(activityDTO.getProductId(), savedActivity.getProductId());
        assertEquals(activityDTO.getEmployeeId(), savedActivity.getEmployeeId());
        assertEquals(activityDTO.getPartId(), savedActivity.getPartId());
    }

    @Test
    void testBorrowAndReturnFlow() {
        // Arrange
        ActivityDTO borrowDTO = new ActivityDTO();
        borrowDTO.setProductId(1);
        borrowDTO.setEmployeeId(1);
        borrowDTO.setPartId(1);
        borrowDTO.setOperateTime(LocalDateTime.now());
        borrowDTO.setAction(ActivityAction.BORROW);

        ActivityDTO returnDTO = new ActivityDTO();
        returnDTO.setProductId(1);
        returnDTO.setEmployeeId(1);
        returnDTO.setPartId(1);
        returnDTO.setOperateTime(LocalDateTime.now());
        returnDTO.setAction(ActivityAction.RETURN);

        // Act
        activityService.borrow(borrowDTO);
        activityService.returnItem(returnDTO);
        List<ActivityDTO> activities = activityService.getActivities(1);

        // Assert
        assertTrue(activities.size() >= 2);
        ActivityDTO borrowActivity = activities.get(0);
        ActivityDTO returnActivity = activities.get(1);
        
        assertEquals(ActivityAction.BORROW, borrowActivity.getAction());
        assertEquals(ActivityAction.RETURN, returnActivity.getAction());
        assertEquals(borrowDTO.getProductId(), borrowActivity.getProductId());
        assertEquals(returnDTO.getProductId(), returnActivity.getProductId());
    }

    @Test
    void testStockOutFlow() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());
        activityDTO.setAction(ActivityAction.STOCK_OUT);

        // Act
        activityService.stockOut(activityDTO);
        List<ActivityDTO> activities = activityService.getActivities(1);

        // Assert
        assertFalse(activities.isEmpty());
        ActivityDTO savedActivity = activities.get(0);
        assertEquals(ActivityAction.STOCK_OUT, savedActivity.getAction());
        assertEquals(activityDTO.getProductId(), savedActivity.getProductId());
        assertEquals(activityDTO.getEmployeeId(), savedActivity.getEmployeeId());
        assertEquals(activityDTO.getPartId(), savedActivity.getPartId());
    }

    @Test
    void testGetActivities_Admin() {
        // Arrange
        ActivityDTO activity1 = new ActivityDTO();
        activity1.setProductId(1);
        activity1.setEmployeeId(1);
        activity1.setPartId(1);
        activity1.setOperateTime(LocalDateTime.now());
        activity1.setAction(ActivityAction.STOCK_IN);

        ActivityDTO activity2 = new ActivityDTO();
        activity2.setProductId(2);
        activity2.setEmployeeId(2);
        activity2.setPartId(2);
        activity2.setOperateTime(LocalDateTime.now());
        activity2.setAction(ActivityAction.STOCK_IN);

        // Act
        activityService.stockIn(activity1);
        activityService.stockIn(activity2);
        List<ActivityDTO> activities = activityService.getActivities(1); // Assuming user 1 is admin

        // Assert
        assertTrue(activities.size() >= 2);
    }
} 