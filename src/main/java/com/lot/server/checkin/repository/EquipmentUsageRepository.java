package com.lot.server.checkin.repository;

import com.lot.server.checkin.domain.entity.EquipmentUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentUsageRepository extends JpaRepository<EquipmentUsage, Integer> {
    Optional<EquipmentUsage> findBySerialNumber(String serialNumber);
}
