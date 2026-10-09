package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByUserId(Long userId);

    @Query("""
           SELECT COALESCE(SUM(t.totalFreight), 0)
           FROM Trip t
           WHERE t.user.id = :userId
           AND t.tripDate LIKE CONCAT(:month, '%')
           """)
    Double getMonthlyRevenue(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}