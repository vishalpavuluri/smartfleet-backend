package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Entity.VehicleDocument;
import com.smartfleet.Repository.VehicleDocumentRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class VehicleDocumentService {

    private final VehicleDocumentRepository documentRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public VehicleDocumentService(
            VehicleDocumentRepository documentRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.documentRepository = documentRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public VehicleDocument addDocument(VehicleDocument document) {

        User user = currentUserService.getCurrentUser();

        if (document.getVehicle() == null ||
            document.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(document.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add Documents Only To Your Own Vehicle"
            );
        }

        document.setVehicle(vehicle);

        return documentRepository.save(document);
    }

    public List<VehicleDocument> getDocuments() {

        User user = currentUserService.getCurrentUser();

        return documentRepository
                .findByVehicleUserId(user.getId());
    }
}