package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.Maintenance;

public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByVehicleUserId(Long userId);

    @Query("""
           SELECT COALESCE(SUM(m.serviceCost), 0)
           FROM Maintenance m
           WHERE m.vehicle.user.id = :userId
           AND m.serviceDate LIKE CONCAT(:month, '%')
           """)
    Double getMonthlyMaintenanceCost(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}