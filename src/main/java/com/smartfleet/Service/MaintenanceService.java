package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Maintenance;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.MaintenanceRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public MaintenanceService(
            MaintenanceRepository maintenanceRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Maintenance addMaintenance(Maintenance maintenance) {

        User user = currentUserService.getCurrentUser();

        if (maintenance.getVehicle() == null ||
            maintenance.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(maintenance.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add Maintenance Only To Your Own Vehicle"
            );
        }

        maintenance.setVehicle(vehicle);

        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getMaintenance() {

        User user = currentUserService.getCurrentUser();

        return maintenanceRepository
                .findByVehicleUserId(user.getId());
    }
}