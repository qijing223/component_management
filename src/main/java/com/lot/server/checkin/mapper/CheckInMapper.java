package com.lot.server.checkin.mapper;

import com.lot.server.checkin.domain.entity.EquipmentUsage;
import com.lot.server.checkin.domain.entity.EmployeeActivity;
import com.lot.server.checkin.domain.model.CheckInDTO;
import com.lot.server.checkin.domain.model.ItemStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    CheckInMapper INSTANCE = Mappers.getMapper(CheckInMapper.class);

    /**
     * Convert CheckInDTO to EquipmentUsage (For check-in and check-out operations)
     */
    @Mapping(target = "usageId", ignore = true) // ID is auto-generated
    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "serialNumber", target = "serialNumber")
    EquipmentUsage toEquipmentUsage(CheckInDTO checkInDTO);

    /**
     * Convert EquipmentUsage to ItemStatusDTO (For checkStatus API)
     */
    @Mapping(source = "status", target = "checkedOut", qualifiedByName = "mapStatus")
    @Mapping(source = "employeeId", target = "borrowedBy")
    @Mapping(source = "operateTime", target = "borrowedAt")
    ItemStatusDTO toItemStatusDTO(EquipmentUsage equipmentUsage);

    /**
     * Convert CheckInDTO to EmployeeActivity (For logging check-in/check-out actions)
     */
    @Mapping(target = "activityId", ignore = true) // ID is auto-generated
    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "serialNumber", target = "serialNumber")
    @Mapping(source = "action", target = "action")
    EmployeeActivity toEmployeeActivity(CheckInDTO checkInDTO);
}
