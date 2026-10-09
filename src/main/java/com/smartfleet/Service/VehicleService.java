package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Vehicle addVehicle(Vehicle vehicle) {

        User user = currentUserService.getCurrentUser();

        vehicle.setUser(user);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehicles() {

        User user = currentUserService.getCurrentUser();

        return vehicleRepository.findByUserId(user.getId());
    }
}