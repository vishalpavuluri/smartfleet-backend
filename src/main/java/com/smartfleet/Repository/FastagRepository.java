package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Fastag;

public interface FastagRepository
        extends JpaRepository<Fastag, Long> {

    List<Fastag> findByVehicleUserId(Long userId);
}