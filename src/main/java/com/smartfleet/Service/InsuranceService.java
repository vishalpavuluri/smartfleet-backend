package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Insurance;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.InsuranceRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public InsuranceService(
            InsuranceRepository insuranceRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.insuranceRepository = insuranceRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Insurance addInsurance(Insurance insurance) {

        User user = currentUserService.getCurrentUser();

        if (insurance.getVehicle() == null ||
            insurance.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(insurance.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add Insurance Only To Your Own Vehicle"
            );
        }

        insurance.setVehicle(vehicle);

        return insuranceRepository.save(insurance);
    }

    public List<Insurance> getInsurance() {

        User user = currentUserService.getCurrentUser();

        return insuranceRepository
                .findByVehicleUserId(user.getId());
    }
}