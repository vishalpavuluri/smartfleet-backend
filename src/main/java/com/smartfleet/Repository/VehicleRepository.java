package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Vehicle;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByUserId(Long userId);
}