package com.smartfleet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.FastagTransaction;

public interface FastagTransactionRepository
        extends JpaRepository<FastagTransaction, Long> {

    @Query("""
           SELECT COALESCE(SUM(f.amount), 0)
           FROM FastagTransaction f
           WHERE f.fastag.vehicle.user.id = :userId
           AND f.transactionDate LIKE CONCAT(:month, '%')
           AND UPPER(f.transactionType) = 'TOLL'
           """)
    Double getMonthlyFastagExpense(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}