package com.smartfleet.Controller;

import com.smartfleet.Entity.Insurance;
import com.smartfleet.Service.InsuranceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin
public class InsuranceController {

    private final InsuranceService service;

    public InsuranceController(InsuranceService service) {
        this.service = service;
    }

    @PostMapping
    public Insurance addInsurance(@RequestBody Insurance insurance) {
        return service.addInsurance(insurance);
    }

    @GetMapping
    public List<Insurance> getInsurance() {
        return service.getInsurance();
    }
}