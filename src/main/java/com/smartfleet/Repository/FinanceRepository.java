package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Finance;

public interface FinanceRepository
        extends JpaRepository<Finance, Long> {

    List<Finance> findByVehicleUserId(Long userId);
}