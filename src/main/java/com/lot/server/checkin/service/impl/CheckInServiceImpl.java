package com.lot.server.checkin.service.impl;

import com.lot.server.checkin.domain.entity.EmployeeActivity;
import com.lot.server.checkin.domain.entity.EquipmentUsage;
import com.lot.server.checkin.domain.entity.EquipmentStatus;
import com.lot.server.checkin.domain.entity.ActivityAction;
import com.lot.server.checkin.domain.model.CheckInDTO;
import com.lot.server.checkin.domain.model.ItemStatusDTO;
import com.lot.server.checkin.repository.EquipmentUsageRepository;
import com.lot.server.checkin.repository.EmployeeActivityRepository;
import com.lot.server.checkin.service.CheckInService;
import com.lot.server.common.bean.ResultTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private EquipmentUsageRepository equipmentUsageRepository;

    @Autowired
    private EmployeeActivityRepository employeeActivityRepository;

    /**
     * Process device return (Check-in).
     */
    @Override
    public ResultTO<Void> checkIn(CheckInDTO checkInDTO) {
        Optional<EquipmentUsage> equipmentOpt = equipmentUsageRepository.findBySerialNumber(checkInDTO.getSerialNumber());

        // Validate device status
        if (equipmentOpt.isEmpty() || equipmentOpt.get().getStatus() != EquipmentStatus.BORROW) {
            return new ResultTO<>(400, "Item is not currently checked out", null);
        }

        EquipmentUsage equipment = equipmentOpt.get();
        equipment.setStatus(EquipmentStatus.RETURN);
        equipment.setEmployeeId(null); // Clear employee ID upon return
        equipment.setOperateTime(LocalDateTime.now());
        equipmentUsageRepository.save(equipment);

        // Record return action in activity log
        EmployeeActivity activity = new EmployeeActivity();
        activity.setEmployeeId(checkInDTO.getEmployeeId());
        activity.setProductId(checkInDTO.getProductId());
        activity.setSerialNumber(checkInDTO.getSerialNumber());
        activity.setAction(ActivityAction.STOCK_IN);
        activity.setOperateTime(LocalDateTime.now());
        employeeActivityRepository.save(activity);

        return new ResultTO<>(200, "Item successfully checked in", null);
    }

    /**
     * Process device checkout (Check-out).
     */
    @Override
    public ResultTO<Void> checkOut(CheckInDTO checkInDTO) {
        Optional<EquipmentUsage> equipmentOpt = equipmentUsageRepository.findBySerialNumber(checkInDTO.getSerialNumber());

        // Validate device status
        if (equipmentOpt.isEmpty() || equipmentOpt.get().getStatus() != EquipmentStatus.RETURN) {
            return new ResultTO<>(400, "Item is not available for check-out", null);
        }

        EquipmentUsage equipment = equipmentOpt.get();
        equipment.setStatus(EquipmentStatus.BORROW);
        equipment.setEmployeeId(checkInDTO.getEmployeeId()); // Assign employee ID
        equipment.setOperateTime(LocalDateTime.now()); // ✅ Fix: Use LocalDateTime
        equipmentUsageRepository.save(equipment);

        // Record checkout action in activity log
        EmployeeActivity activity = new EmployeeActivity();
        activity.setEmployeeId(checkInDTO.getEmployeeId());
        activity.setProductId(checkInDTO.getProductId());
        activity.setSerialNumber(checkInDTO.getSerialNumber());
        activity.setAction(ActivityAction.STOCK_OUT);
        activity.setOperateTime(LocalDateTime.now()); // ✅ Fix: Use LocalDateTime
        employeeActivityRepository.save(activity);

        return new ResultTO<>(200, "Item successfully checked out", null);
    }

    /**
     * Retrieve the current status of a device.
     */
    @Override
    public ResultTO<ItemStatusDTO> checkStatus(Integer productId, String serialNumber) {
        Optional<EquipmentUsage> equipmentOpt = equipmentUsageRepository.findBySerialNumber(serialNumber);

        if (equipmentOpt.isEmpty()) {
            return new ResultTO<>(400, "Item not found", null);
        }

        EquipmentUsage equipment = equipmentOpt.get();
        ItemStatusDTO statusDTO = new ItemStatusDTO();
        statusDTO.setCheckedOut(equipment.getStatus() == EquipmentStatus.BORROW);
        statusDTO.setBorrowedBy(equipment.getEmployeeId());
        statusDTO.setBorrowedAt(equipment.getOperateTime());

        return new ResultTO<>(200, "Item status retrieved successfully", statusDTO);
    }
}
