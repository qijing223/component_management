package com.lot.server.activity.service;

import com.lot.server.activity.domain.entity.ActivityAction;
import com.lot.server.activity.domain.model.ActivityDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance tests for ActivityService
 */
@Disabled
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ActivityServicePerformanceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    void testConcurrentStockIn() throws InterruptedException {
        // Arrange
        int numberOfThreads = 10;
        int operationsPerThread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Act
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    ActivityDTO activityDTO = new ActivityDTO();
                    activityDTO.setProductId(threadId);
                    activityDTO.setEmployeeId(threadId);
                    activityDTO.setPartId(j);
                    activityDTO.setOperateTime(LocalDateTime.now());
                    activityService.stockIn(activityDTO);
                }
            }, executorService);
            futures.add(future);
        }

        // Wait for all operations to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long endTime = System.currentTimeMillis();

        // Assert
        long totalTime = endTime - startTime;
        long totalOperations = numberOfThreads * operationsPerThread;
        double operationsPerSecond = (totalOperations * 1000.0) / totalTime;

        System.out.println("Total time: " + totalTime + "ms");
        System.out.println("Operations per second: " + operationsPerSecond);

        // Verify results
        List<ActivityDTO> activities = activityService.getActivities(0);
        assertTrue(activities.size() >= totalOperations);
    }

    @Test
    void testBulkOperations() {
        // Arrange
        int numberOfOperations = 1000;
        List<ActivityDTO> activities = new ArrayList<>();

        // Act
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfOperations; i++) {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setProductId(i % 10); // Cycle through 10 products
            activityDTO.setEmployeeId(i % 5); // Cycle through 5 employees
            activityDTO.setPartId(i);
            activityDTO.setOperateTime(LocalDateTime.now());

            switch (i % 4) {
                case 0:
                    activityService.stockIn(activityDTO);
                    break;
                case 1:
                    activityService.stockOut(activityDTO);
                    break;
                case 2:
                    activityService.borrow(activityDTO);
                    break;
                case 3:
                    activityService.returnItem(activityDTO);
                    break;
            }
            activities.add(activityDTO);
        }

        long endTime = System.currentTimeMillis();

        // Assert
        long totalTime = endTime - startTime;
        double operationsPerSecond = (numberOfOperations * 1000.0) / totalTime;

        System.out.println("Total time for bulk operations: " + totalTime + "ms");
        System.out.println("Operations per second: " + operationsPerSecond);

        // Verify results
        List<ActivityDTO> savedActivities = activityService.getActivities(0);
        assertTrue(savedActivities.size() >= numberOfOperations);
    }

    @Test
    void testGetActivitiesPerformance() {
        // Arrange
        int numberOfActivities = 1000;
        
        // Create test data
        for (int i = 0; i < numberOfActivities; i++) {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setProductId(i % 10);
            activityDTO.setEmployeeId(i % 5);
            activityDTO.setPartId(i);
            activityDTO.setOperateTime(LocalDateTime.now());
            activityService.stockIn(activityDTO);
        }

        // Act
        long startTime = System.currentTimeMillis();
        List<ActivityDTO> activities = activityService.getActivities(0);
        long endTime = System.currentTimeMillis();

        // Assert
        long totalTime = endTime - startTime;
        System.out.println("Time to retrieve " + numberOfActivities + " activities: " + totalTime + "ms");
        assertTrue(activities.size() >= numberOfActivities);
    }
} 