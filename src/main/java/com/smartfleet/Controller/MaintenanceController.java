package com.smartfleet.Controller;

import com.smartfleet.Entity.Maintenance;
import com.smartfleet.Service.MaintenanceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin
public class MaintenanceController {

    private final MaintenanceService service;

    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @PostMapping
    public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
        return service.addMaintenance(maintenance);
    }

    @GetMapping
    public List<Maintenance> getMaintenance() {
        return service.getMaintenance();
    }
}