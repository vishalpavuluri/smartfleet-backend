package com.smartfleet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartfleet.Entity.FinancePayment;

public interface FinancePaymentRepository
        extends JpaRepository<FinancePayment, Long> {

    @Query("""
           SELECT COALESCE(SUM(f.amount), 0)
           FROM FinancePayment f
           WHERE f.finance.vehicle.user.id = :userId
           AND f.paymentDate LIKE CONCAT(:month, '%')
           AND UPPER(f.status) = 'PAID'
           """)
    Double getMonthlyEmiPayments(
            @Param("userId") Long userId,
            @Param("month") String month
    );
}