package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Driver;

public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    List<Driver> findByUserId(Long userId);
}