package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Fastag;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.FastagRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class FastagService {

    private final FastagRepository fastagRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public FastagService(
            FastagRepository fastagRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.fastagRepository = fastagRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Fastag addFastag(Fastag fastag) {

        User user = currentUserService.getCurrentUser();

        if (fastag.getVehicle() == null ||
            fastag.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(fastag.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add FASTag Only To Your Own Vehicle"
            );
        }

        fastag.setVehicle(vehicle);

        return fastagRepository.save(fastag);
    }

    public List<Fastag> getFastags() {

        User user = currentUserService.getCurrentUser();

        return fastagRepository
                .findByVehicleUserId(user.getId());
    }
}