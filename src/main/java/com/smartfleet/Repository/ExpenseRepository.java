package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.Expense;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByVehicleUserId(Long userId);

    @Query("""
           SELECT COALESCE(SUM(e.amount), 0)
           FROM Expense e
           WHERE e.vehicle.user.id = :userId
           AND e.expenseDate LIKE CONCAT(:month, '%')
           AND UPPER(e.expenseType) NOT IN
           ('EMI', 'SERVICE', 'INSURANCE', 'FASTAG', 'TOLL')
           """)
    Double getMonthlyExpenses(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}