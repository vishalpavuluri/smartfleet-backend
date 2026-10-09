package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.BusinessPayment;

public interface BusinessPaymentRepository
        extends JpaRepository<BusinessPayment, Long> {

    List<BusinessPayment> findByTripUserId(Long userId);
}