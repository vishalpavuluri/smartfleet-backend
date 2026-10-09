package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.Insurance;

public interface InsuranceRepository
        extends JpaRepository<Insurance, Long> {

    List<Insurance> findByVehicleUserId(Long userId);

    @Query("""
           SELECT COALESCE(SUM(i.premium), 0)
           FROM Insurance i
           WHERE i.vehicle.user.id = :userId
           AND i.startDate LIKE CONCAT(:month, '%')
           """)
    Double getMonthlyInsuranceCost(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}