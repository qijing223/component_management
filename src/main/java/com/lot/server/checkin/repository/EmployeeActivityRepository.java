package com.lot.server.checkin.repository;

import com.lot.server.checkin.domain.entity.EmployeeActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, Integer> {
}
