package com.smartfleet.Controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public Vehicle addVehicle(
            @Valid @RequestBody Vehicle vehicle) {

        return service.addVehicle(vehicle);
    }

    @GetMapping
    public List<Vehicle> getVehicles() {

        return service.getVehicles();
    }
}