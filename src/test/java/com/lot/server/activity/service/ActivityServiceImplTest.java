package com.lot.server.activity.service;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.entity.EmployeeActivity;
import com.lot.server.activity.domain.model.ActivityDTO;
import com.lot.server.activity.mapper.ActivityMapper;
import com.lot.server.activity.service.impl.ActivityServiceImpl;
import com.lot.server.employee.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ActivityServiceImpl
 */
@Disabled
@SpringBootTest
public class ActivityServiceImplTest {

    @Mock
    private ActivityMapper activityMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activityService = new ActivityServiceImpl(activityMapper, employeeMapper);
    }

    @Test
    void testStockIn() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Act
        activityService.stockIn(activityDTO);

        // Assert
        verify(activityMapper, times(1)).insert(any(EmployeeActivity.class));
    }

    @Test
    void testStockOut() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Act
        activityService.stockOut(activityDTO);

        // Assert
        verify(activityMapper, times(1)).insert(any(EmployeeActivity.class));
    }

    @Test
    void testBorrow() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Act
        activityService.borrow(activityDTO);

        // Assert
        verify(activityMapper, times(1)).insert(any(EmployeeActivity.class));
    }

    @Test
    void testReturnItem() {
        // Arrange
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setProductId(1);
        activityDTO.setEmployeeId(1);
        activityDTO.setPartId(1);
        activityDTO.setOperateTime(LocalDateTime.now());

        // Act
        activityService.returnItem(activityDTO);

        // Assert
        verify(activityMapper, times(1)).insert(any(EmployeeActivity.class));
    }

    @Test
    void testGetActivities_Admin() {
        // Arrange
        Integer userId = 1;
        when(employeeMapper.getEmployeeTypeById(userId)).thenReturn("Admin");
        
        EmployeeActivity activity1 = new EmployeeActivity();
        activity1.setActivityId(1);
        activity1.setProductId(1);
        activity1.setEmployeeId(1);
        activity1.setPartId(1);
        activity1.setAction(ActivityAction.STOCK_IN);
        activity1.setOperateTime(LocalDateTime.now());

        EmployeeActivity activity2 = new EmployeeActivity();
        activity2.setActivityId(2);
        activity2.setProductId(2);
        activity2.setEmployeeId(2);
        activity2.setPartId(2);
        activity2.setAction(ActivityAction.BORROW);
        activity2.setOperateTime(LocalDateTime.now());

        when(activityMapper.selectAll()).thenReturn(Arrays.asList(activity1, activity2));

        // Act
        List<ActivityDTO> result = activityService.getActivities(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(activityMapper, times(1)).selectAll();
    }

    @Test
    void testGetActivities_RegularUser() {
        // Arrange
        Integer userId = 1;
        when(employeeMapper.getEmployeeTypeById(userId)).thenReturn("User");
        
        EmployeeActivity activity = new EmployeeActivity();
        activity.setActivityId(1);
        activity.setProductId(1);
        activity.setEmployeeId(userId);
        activity.setPartId(1);
        activity.setAction(ActivityAction.STOCK_IN);
        activity.setOperateTime(LocalDateTime.now());

        when(activityMapper.selectByEmployeeId(userId)).thenReturn(Arrays.asList(activity));

        // Act
        List<ActivityDTO> result = activityService.getActivities(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getEmployeeId());
        verify(activityMapper, times(1)).selectByEmployeeId(userId);
    }

    @Test
    void testGetActivities_UnknownUserType() {
        // Arrange
        Integer userId = 1;
        when(employeeMapper.getEmployeeTypeById(userId)).thenReturn("Unknown");
        
        EmployeeActivity activity = new EmployeeActivity();
        activity.setActivityId(1);
        activity.setProductId(1);
        activity.setEmployeeId(userId);
        activity.setPartId(1);
        activity.setAction(ActivityAction.STOCK_IN);
        activity.setOperateTime(LocalDateTime.now());

        when(activityMapper.selectByEmployeeId(userId)).thenReturn(Arrays.asList(activity));

        // Act
        List<ActivityDTO> result = activityService.getActivities(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getEmployeeId());
        verify(activityMapper, times(1)).selectByEmployeeId(userId);
    }

    @Test
    void testGetActivities_EmptyResult() {
        // Arrange
        Integer userId = 1;
        when(employeeMapper.getEmployeeTypeById(userId)).thenReturn("Admin");
        when(activityMapper.selectAll()).thenReturn(Arrays.asList());

        // Act
        List<ActivityDTO> result = activityService.getActivities(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(activityMapper, times(1)).selectAll();
    }
} 