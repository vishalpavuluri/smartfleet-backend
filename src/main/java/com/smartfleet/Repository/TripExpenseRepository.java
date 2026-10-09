package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.TripExpense;

public interface TripExpenseRepository
        extends JpaRepository<TripExpense, Long> {

    List<TripExpense> findByTripId(Long tripId);

    List<TripExpense> findByTripUserId(Long userId);

    @Query("""
           SELECT COALESCE(SUM(t.amount), 0)
           FROM TripExpense t
           WHERE t.trip.user.id = :userId
           AND t.expenseDate LIKE CONCAT(:month, '%')
           """)
    Double getMonthlyTripExpenses(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}