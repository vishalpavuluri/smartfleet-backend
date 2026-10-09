package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.VehicleDocument;

public interface VehicleDocumentRepository
        extends JpaRepository<VehicleDocument, Long> {

    List<VehicleDocument> findByVehicleUserId(Long userId);
}